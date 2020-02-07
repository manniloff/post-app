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
        stage('Docker') {
            steps {
                echo 'Start compose..'
                bat 'docker-compose -f docker-compose/docker-compose.yml up'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}