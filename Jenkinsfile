pipeline {
    agent any
    tools {
        jdk 'jdk8'
    }
    environment {
        APP_NAME = 'server-339' // 修改APP名字
        APP_PORT = 11339 // 修改端口号
        REDIS_NAME = 'redis-server'  // Redis容器名称
        REDIS_HOST = 'redis-server'  // Docker网络中的Redis服务名
        REDIS_CONTAINER_PORT = 6379  // Redis容器内部端口
    }
    stages {
        stage('build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests=true'
                }
            }
        }

        stage('deploy') {
            steps {
                script {
                    sh 'docker network create app-network || true'

                    sh 'docker stop $APP_NAME || true'
                    sh 'docker rm $APP_NAME || true'

                    // 移除旧版本镜像
                    sh 'docker rmi $APP_NAME || true'
                    
                    // 清理所有悬空镜像（<none>:<none>）
                    sh 'docker images -f "dangling=true" -q | xargs -r docker rmi'

                    sh 'docker build -t $APP_NAME .'

                    // 应用容器内部仍使用Redis的6379端口通信（容器间网络）
                    sh """
                        docker run -d --name $APP_NAME \
                        --network app-network \
                        -p 0.0.0.0:$APP_PORT:$APP_PORT \
                        -e SPRING_REDIS_HOST=$REDIS_HOST \
                        -e SPRING_REDIS_PORT=$REDIS_CONTAINER_PORT \
                        --restart=always \
                        $APP_NAME \
                        --server.port=$APP_PORT
                    """
                }
            }
        }
    }
}
