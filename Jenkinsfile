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
                script {
                    // Build the project using Maven
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
                script {
                    // Directly copy the WAR file to Tomcat's webapps directory
                    sh """
                        cp target/conorspetitions.war /opt/tomcat/webapps/
                        sudo chown conor:conor /opt/tomcat/webapps/conorspetitions.war
                        sudo chmod 755 /opt/tomcat/webapps/conorspetitions.war
                    """
                }
            }
        }

        stage('Restart Tomcat') {
            steps {
                script {
                    // Restart Tomcat using systemctl
                    sh """
                        sudo /opt/tomcat/bin/shutdown.sh
                        sleep 5
                        sudo /opt/tomcat/bin/startup.sh
                    """
                }
            }
        }
    }
}
