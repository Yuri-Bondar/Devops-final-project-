pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        // Source JSP file inside the repository
        SRC = "${WORKSPACE}\\adamliadadiramityuri\\index.jsp"

        // Safe deploy directory (NO Program Files)
        DEPLOY_DIR = "C:\\deploy\\devops"

        // Health check URL
        HEALTH_URL = "http://localhost:8080/Devops-final-project-/adamliadadiramityuri/"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare Deploy Directory') {
            steps {
                bat '''
                @echo off
                if not exist "%DEPLOY_DIR%" (
                    mkdir "%DEPLOY_DIR%"
                )
                '''
            }
        }

        stage('Deploy JSP (Safe Copy)') {
            steps {
                bat '''
                @echo off
                echo SRC=%SRC%
                echo DEPLOY_DIR=%DEPLOY_DIR%

                if not exist "%SRC%" (
                    echo ERROR: Source JSP not found
                    exit /b 1
                )

                copy /Y "%SRC%" "%DEPLOY_DIR%\\index.jsp"
                if errorlevel 1 exit /b 1

                echo Deploy completed successfully
                '''
            }
        }

        stage('Monitor Check') {
            steps {
                bat '''
                @echo off
                echo Checking %HEALTH_URL%

                powershell -NoProfile -Command "
                try {
                    $r = Invoke-WebRequest -UseBasicParsing -TimeoutSec 10 -Uri '%HEALTH_URL%' -ErrorAction Stop
                    Write-Host ('STATUS CODE: ' + $r.StatusCode)
                    if ($r.StatusCode -ge 200 -and $r.StatusCode -lt 400) { exit 0 } else { exit 1 }
                }
                catch {
                    Write-Host ('ERROR: ' + $_.Exception.Message)
                    exit 1
                }
                "
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline finished successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
