node{
    stage('git clone'){
        updateGitlabCommitStatus name: 'jenkins', state: 'pending'
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], browser: [$class: 'GitLab', repoUrl: 'http://212.129.149.40'], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '3', url: 'http://212.129.149.40/171250507_IIICEStseB/Backend-iiicestseb']]])
        echo '=== git clone end ==='
    }
    
    stage('clean'){
        updateGitlabCommitStatus name: 'jenkins', state: 'running'
        sh label: 'test', returnStatus: true, script: 'mvn clean'
    }
    
    stage('test'){
        updateGitlabCommitStatus name: 'jenkins', state: 'running'
        sh label: 'test', returnStatus: true, script: 'mvn test -P test'
    }
    
    stage('report'){
        jacoco classPattern: '**/*serviceImpl,**/*controller', sourceInclusionPattern: '**/*ServiceImpl.java,**/*Controller.java'
    }
    
    stage('clean test'){
        updateGitlabCommitStatus name: 'jenkins', state: 'running'
        sh label: 'test', returnStatus: true, script: 'mvn clean'
    }
    
    stage('package'){
        sh label: 'package', returnStatus: true, script: 'mvn package -P prod -Dmaven.test.skip=true'
    }
    
    stage('deploy'){
		sshPublisher(publishers: [sshPublisherDesc(configName: 'tencent', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '''cd /var/www
./stop.sh
./start.sh''', execTimeout: 5000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: 'target/', sourceFiles: 'target/*.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
		updateGitlabCommitStatus name: 'jenkins', state: 'success'
        echo '=== depoly end ==='
	}
    
    stage('archive'){
        archiveArtifacts allowEmptyArchive: true, artifacts: 'target/backend-0.0.1.jar', onlyIfSuccessful: true
        updateGitlabCommitStatus name: 'jenkins', state: 'success'
    }
}