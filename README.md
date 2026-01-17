# Devops-final-project-

## Jenkins CI/CD – Stage 4

We created a Jenkins Freestyle Job named `deploy-jsp-to-tomcat`.

Job flow:
1. Jenkins polls GitHub every 5 minutes (Poll SCM).
2. On change, Jenkins pulls the repository.
3. Jenkins copies `index.jsp` to Tomcat webapps folder.
4. Tomcat serves the updated page automatically.

Jenkins runs on port 8082.
Tomcat runs on port 8081.
