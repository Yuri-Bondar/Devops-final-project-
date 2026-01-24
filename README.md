# Devops-final-project-

DevOps Final Project: JSP Application & CI/CD Pipeline
This project demonstrates a complete CI/CD pipeline implementation for a Java Server Pages (JSP) web application, utilizing modern DevOps tools for automation, performance testing, and continuous monitoring.

🚀 Project Overview
The application is a simple JSP-based web interface that allows users to submit text and view the results. The infrastructure is designed to be fully automated, deploying to an Apache Tomcat 9 server on a Linux environment via a Jenkins Pipeline.

🛠 Technologies & Tools
Web Server: Apache Tomcat 9.

CI/CD Engine: Jenkins (Declarative Pipeline).

Build Tool: Maven.

Testing Suite:

Selenium IDE: Automated UI and functional validations.

Gatling (Scala): High-performance Load and Stress testing.

Monitoring: UptimeRobot API integration for availability tracking.

🏗 Pipeline Architecture
The Jenkinsfile defines a multi-stage lifecycle to ensure code quality and deployment stability:

Initialize & Checkout: Verifies the environment and fetches the latest source code from GitHub.

Deploy to Tomcat: Automatically deploys the index.jsp file to the production directory: /var/lib/tomcat9/webapps/.

External Monitoring Status: Queries the UptimeRobot API to ensure the public endpoint is reachable.

Automated Quality Tests: A conditional block that skips heavy testing during routine 5-minute cron checks but runs during SCM changes or manual triggers:

Selenium Tests: Executes 4 functional validations using a headless Firefox browser.

Load Test: Simulates 27 concurrent users over 3 minutes to verify stability.

Stress Test: Ramps up to 70 concurrent users to identify the system's breaking point.

📊 Testing Strategy
Selenium UI Validations
We implemented 4 Hard Assertions to enforce a Fail-Fast methodology:

Verify Header (h1): Confirms the page identity ("DevOps Project").

Verify Welcome Message (h2): Ensures static JSP content is rendered correctly.

Verify Result Box: Confirms the server-side logic displays the output container after submission.

Verify Data Integrity: Matches the exact submitted string ("DevOpsProject") against the displayed output.

Performance Benchmarking (Gatling)
Load Simulation: Ramps from 1 to 27 users in 30 seconds, maintaining steady pressure for 3 minutes.

Stress Simulation: Linear ramp-up to 70 users over 3 minutes to determine maximum throughput.

Quality Gates: The pipeline fails if the success rate drops below 90% or if response times exceed defined thresholds.

📋 Prerequisites & Configuration
To replicate this environment, ensure the following are configured:

Jenkins Plugins: NodeJS, Maven, Gatling, and Pipeline.

Credentials: A Jenkins secret string named uptimerobot-api-key.

Environment: A Linux server with Tomcat 9 and a public IP address.

Authors: Adam, Liad, Adir, Amit, Yuri.