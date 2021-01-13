final sharedlib = library('sharedlib@master')
def call(Map pipelineParams) {

pipeline {
    agent any
    environment {
    developer = 'ravali'
    QA = 'ravali'
    PO = 'ravali'
    }
    stages {
        stage('Compile') {
            steps {
                echo 'npm clean install'
            }
        }
        stage('Unit Test') {
            steps {
                echo 'npm test'
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'sonar analysis'
            }
        }
        stage('Publish to Artifactory') {
            when {
                anyOf {
                branch 'master'
                branch 'hotfix-1'
                }    
            }
            steps {
                echo 'publish npm package to artifactory'
            }
        }
        stage('Dev Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'deploy to Dev env'
            }
        }
        stage('Dev-->QA') {
            agent none
            when {
                branch 'master'
            }
            steps {
                input message: 'hey developer would you like to promote to QA env', ok: 'proceed', submitter: env.developer, submitterParameter: 'approver'
            }
        }    
        stage('QA Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'QA deploy'
            }
        }    
        stage ('QA-->Staging') {
            agent none
            when {
                branch 'master'
            }
            steps {
                input message: 'hey QA Engineer would you like to promote to staging env', ok: 'proceed', submitter: env.QA, submitterParameter: 'approver'
            }  
        }
        stage('Staging Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'staging deploy'
            }
        }
        stage ('Staging-->Production') {
            agent none
            when {
                branch 'master'
            }
            steps {
                input message: 'hey PO would you like to promote to prod env', ok: 'proceed', submitter: env.PO, submitterParameter: 'approver'
            }  
        }
        stage('Production Deploy') {
            when {
                anyOf {
                branch 'master'
                }    
            }
            steps {
                echo 'Production deploy'
            }    
        }
    }
}
}

