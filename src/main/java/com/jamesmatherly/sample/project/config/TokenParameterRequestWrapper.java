package com.jamesmatherly.sample.project.config;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class TokenParameterRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String[]> modifiedParameters;

    public TokenParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        modifiedParameters = new HashMap<>(request.getParameterMap());
        if (modifiedParameters.containsKey("ticker")) {
            String[] tokenValues = modifiedParameters.get("ticker");
            for (int i = 0; i < tokenValues.length; i++) {
                tokenValues[i] = tokenValues[i].toUpperCase();
            }
            modifiedParameters.put("ticker", tokenValues);
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        return modifiedParameters.get(name);
    }

    @Override
    public String getParameter(String name) {
        String[] values = modifiedParameters.get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }
}
