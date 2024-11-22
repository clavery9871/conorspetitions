pipeline {
    agent any
    options {
        timeout(time: 5, unit: 'MINUTES') // Setting a hard timeout for the pipeline
    }
    stages {
        stage('Download Dependencies') {
            steps {
                script {
                    // Run Maven offline dependencies goal to cache them in Jenkins
                    sh 'mvn dependency:go-offline'
                }
            }
        }
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/clavery9871/conorspetitions'
            }
        }
        stage('Build') {
            steps {
                timeout(time: 2, unit: 'MINUTES') { // Timeout specifically for this stage
                    sh 'mvn clean install'
                }
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
