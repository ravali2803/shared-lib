final sharedlib = library('sharedlib@master')
def call(Map pipelineParams) {

pipeline {
    agent none
    environment {
    developer = 'ravali'
    QA = 'ravali'
    PO = 'ravali'
    }
    stages {
        stage('Compile') {
            agent any
            steps {
                echo 'npm clean install'
            }
        }
        stage('Unit Test') {
            agent any
            steps {
                echo 'npm test'
            }
        }
        stage('Code Analysis') {
            agent any
            steps {
                echo 'sonar analysis'
            }
        }
        stage('Publish to Artifactory') {
            agent any
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
            agent any
            when {
                branch 'master'
            }
            steps {
                echo 'deploy to Dev env'
            }
        }
        stage('Dev-->QA') {
            when {
                branch 'master'
            }
            steps {
                input message: 'hey developer would you like to promote to QA env', ok: 'proceed', submitter: env.developer, submitterParameter: 'approver'
            }
        }    
        stage('QA Deploy') {
            agent any
            when {
                branch 'master'
            }
            steps {
                echo 'QA deploy'
            }
        }    
        stage ('QA-->Staging') {
            when {
                branch 'master'
            }
            steps {
                input message: 'hey QA Engineer would you like to promote to staging env', ok: 'proceed', submitter: env.QA, submitterParameter: 'approver'
            }  
        }
        stage('Staging Deploy') {
            agent any
            when {
                anyOf {
                    branch 'master'
                    branch 'hotfix-1'
                }
            }
            steps {
                echo 'staging deploy'
            }
        }
        stage ('Staging-->Production') {
            when {
                anyOf {
                    branch 'master'
                    branch 'hotfix-1'
                }    
            }
            steps {
                input message: 'hey PO would you like to promote to prod env', ok: 'proceed', submitter: env.PO, submitterParameter: 'approver'
            }  
        }
        stage('Production Deploy') {
            agent any
            when {
                anyOf {
                    branch 'master'
                    branch 'hotfix-1'
                }    
            }
            steps {
                echo 'Production deploy'
            }    
        }
    }
}
}

