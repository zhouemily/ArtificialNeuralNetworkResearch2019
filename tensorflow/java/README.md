#### How to build
1. install maven
   ```
   mkdir ~/maven
   cd ~/maven
   curl -OJL http://apache.osuosl.org/maven/maven-3/3.6.1/binaries/apache-maven-3.6.1-bin.zip
   unzip apache-maven-3.6.1-bin.zip
   export PATH=~/maven/bin:$PATH
   ```
2. install JDK 1.8, then set JAVA_HOME
   ```
   export JAVA_HOME=<location-JDK1.8-installed>
   ```
3. git clone the repo and cd to this directory, then run
   chmod +x ./build
   ./build
#### How to run
cd to this directory, then run
chmod +x ./run
> ./run

