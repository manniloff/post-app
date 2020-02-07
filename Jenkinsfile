pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                withMaven (maven :'maven_3.5.0'){
                    bat label: '', script: '''bat mvn clean
                    bat mvn package'''
                }
                echo 'Building...'
            }
        }
        stage('Test') {
            agent { docker 'openjdk:8-jre' }
            steps {
                echo 'Testing..'
                sh 'java -version'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}