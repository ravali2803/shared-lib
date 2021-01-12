<<<<<<< HEAD
final sharedlib = library('sharedlib@master')
=======
final sharedlib = @library('shared-lib')
>>>>>>> 16af0a42c25a9ff7fce76856de7bfa868142fccc
def call(Map PipelineParams)

pipeline {
    agent any
    stages {
        stage('Compile') {
            steps {
                echo 'mvn clean install'
            }
        }
        stage('Unit Test') {
            steps {
                echo 'mvn test'
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'sonar analysis'
            }
        }
        stage('Publish to Artifactory') {
            when {
                branch 'master'
            }
            steps {
                echo 'publish to maven repo in artifactory'
            }
        }
