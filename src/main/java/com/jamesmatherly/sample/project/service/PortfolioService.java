package com.jamesmatherly.sample.project.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.jamesmatherly.sample.project.dynamo.Portfolio;
import com.jamesmatherly.sample.project.dynamo.PortfolioRepository;
import com.jamesmatherly.sample.project.dynamo.Trade;
import com.jamesmatherly.sample.project.dynamo.TradeRepository;
import com.jamesmatherly.sample.project.model.Position;
import com.jamesmatherly.sample.project.model.TradeType;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

@Service
public class PortfolioService {
    @Autowired
    PortfolioRepository repository;

    @Autowired
    @Lazy
    TradeRepository tradeRepository;

    @Autowired
    @Lazy
    StockService stockService;

    public Portfolio getPortfolioById(String id) {
        return repository.getById(id);
    }

    public Portfolio updatePortfolio(Portfolio portfolio) {
        return repository.updatePortfolio(portfolio);
    }

    public SdkIterable<Page<Portfolio>> getPortfolioByUser(String username) {
        return repository.getByUsername(username);
    }

    public HashMap<String, Position> getPositions (Portfolio portfolio) {
        SdkIterable<Page<Trade>> i = tradeRepository.getByPortfolioId(portfolio.getId());
        HashMap<String, Position> positions = new HashMap<>();

        i.forEach(page -> page.items().forEach( t -> {
            Position p = positions.getOrDefault(t.getTicker(), new Position());

            float costBasis = p.getCostBasis();
            double currentValue = p.getCurrentValue() == null ? 0 : Double.parseDouble(p.getCurrentValue());
            float quantity = p.getQuantity();
            TradeType tradeType = t.getTradeType();

            if (tradeType.equals(TradeType.BUY)) {
                quantity += t.getQuantity();
                currentValue = currentValue + (t.getValue() * t.getQuantity());
                costBasis = ((p.getCostBasis() * p.getQuantity())+(t.getValue() * t.getQuantity()))/(p.getQuantity() + t.getQuantity());
            } else {
                quantity -= t.getQuantity();
                float effectiveBasis = Math.max(t.getValue(), costBasis);
                currentValue = currentValue - (t.getValue() * t.getQuantity());
                costBasis = ((p.getCostBasis() * p.getQuantity())-(effectiveBasis * t.getQuantity()))/(p.getQuantity() - t.getQuantity());
                costBasis = (p.getQuantity() - t.getQuantity()) == 0 ? 0 : costBasis;
                costBasis = Math.max(costBasis, 0);
            }
            p.setCostBasis(costBasis);
            p.setCurrentValue(String.valueOf(currentValue));
            p.setQuantity(quantity);
            p.setTicker(t.getTicker());
            positions.put(p.getTicker(), p);
        }));

        return positions;
    }
}
