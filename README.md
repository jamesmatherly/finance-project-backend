# yahoo-sample-project
This is a small service that calls the Yahoo Finance API
Created as part of an ongoing effort to learn about AWS

This repo is setup to automatically build a docker image and deploy it to ECS whenever master is updated

The ECS container sits behind a load balancer in AWS. The domain jameslearnscloud.com was registered via Route 53 which routes to the load balancer.

At this time, the only endpoint available is '/controller' which takes query param 'ticker'. The application takes the ticker value and returns the target high price from the Yahoo Finance API.

ex: jameslearnscloud.com/controller?ticker=aapl



Future plans:

Add CI pipeline to build and test new features automatically before deployment

Integration with AWS-hosted NoSQL database

Create more repos with more microservices that interact with each other in AWS

Front end implementation
