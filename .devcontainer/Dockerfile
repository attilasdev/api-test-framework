FROM mcr.microsoft.com/devcontainers/java:17

# Install required packages and Allure
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.24.1/allure-2.24.1.tgz && \
    tar -zxvf allure-2.24.1.tgz -C /opt/ && \
    ln -s /opt/allure-2.24.1/bin/allure /usr/local/bin/allure && \
    rm allure-2.24.1.tgz && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*