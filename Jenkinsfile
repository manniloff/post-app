pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                bat "mvn clean"
                bat "mvn package"
            }
        }
        stage('Deploy') {
            steps {
                echo 'Start compose..'
                bat 'docker-compose -f docker-compose.yml up -d'
            }
        }
    }
}