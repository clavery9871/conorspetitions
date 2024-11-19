pipeline {
    agent any
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/clavery9871/conorspetitions'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
            }
        }
        stage('Deploy') {
            steps {
                input message: 'Deploy?', ok: 'Deploy'
                sh 'scp target/conorspetitions.war ubuntu@16.16.63.170:/opt/tomcat/webapps/'
            }
        }
    }
}
