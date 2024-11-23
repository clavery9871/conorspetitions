pipeline {
    agent any
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
                script {
                    try {
                        sh 'mvn clean install'
                    } catch (Exception e) {
                        echo "Maven build failed: ${e.getMessage()}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
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
                   script {
                       // Deploy WAR to Tomcat using curl (Tomcat Manager)
                       sh """
                       curl -u conor:conor_123 -T target/conorspetitions.war \
                       http://localhost:9090/manager/deploy?path=/conorspetitions&update=true
                       """
                   }
               }
        }
    }
}
