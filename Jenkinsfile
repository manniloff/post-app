pipeline {
    agent any

    stages {
        stage('Build') {
            agent { docker 'maven:3-alpine' }
            steps {
                echo 'Building..'
                sh 'mvn --version'
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