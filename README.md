# yahoo-sample-project
This is a small service that calls the Yahoo Finance API
Created as part of an ongoing effort to learn about AWS

This repo is setup to automatically build a docker image and deploy it to ECS whenever master is updated

The ECS container sits behind a load balancer in AWS. The domain jameslearnscloud.com was registered via Route 53 which routes to the load balancer.

At this time, the only endpoint available is '/controller' which takes query param 'ticker'. The application takes the ticker value and returns the target high price from the Yahoo Finance API.

ex: api.jameslearnscloud.com/controller?ticker=aapl

## Current unresolved issues

- ECS unable to drain and spin up new task on deploy.
    - Cause: Moved from Fargate to EC2 to avoid incurring costs
    - Potential solution 1: For *n* desired task replications, add *n+1* instances to cluster.
- Load balancing not switching automatically.
    - Cause: Moved from Fargate to EC2 to avoid incurring costs
    - Potential solution 1: Add a target group to the load balancer for each instance in cluster. Health checks should handle the rest
    - Potential solution 2: Use the AWS CLI to fetch the IP of the current task. Drain the old one from the load balancer and register the new one.



## Future plans:

Add CI pipeline to build and test new features automatically before deployment

Integration with AWS-hosted NoSQL database

Create more repos with more microservices that interact with each other in AWS

Front end implementation
