pipeline {
    agent any
    tools {
        maven 'mvn' // Usa el nombre configurado en Jenkins
    }
    environment {
        SONARQUBE_ENV = 'sonarqube' // Debe coincidir con el nombre de tu configuración en Jenkins
    }
    stages {
        stage('Build Mascota') {
            steps {
                dir('mascota') {
                    sh 'mvn clean install'
                }
            }
        }
        stage('SonarQube Analysis Mascota') {
            steps {
                dir('mascota') {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        sh '''
                          mvn sonar:sonar \
                          -Dsonar.host.url=http://sonarqube:9000 \
                          -Dsonar.projectKey=SgVet-Mascota \
                          -Dsonar.projectName=SgVet-Mascota
                        '''
                    }
                }
            }
        }
    }
}
