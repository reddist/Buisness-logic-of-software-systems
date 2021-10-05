USER=${1:-265058}
PORT=${2:-17500}
CONFIG=helios.properties
if [ -f $CONFIG ]; then
  mvn -f ../pom.xml package
  FILES=(../target/*.jar)
  JAR=$(basename "${FILES[0]}")
  mv ../target/"${JAR}" ./
  echo "java18 -Dspring.config.location=${CONFIG} -jar ${JAR}" >> start.sh
  scp -P 2222 ./* s"${USER}"@se.ifmo.ru:~/blps/lab1_deploy/
  rm "$JAR" start.sh
#  ssh -P 2222 s"$USER"@se.ifmo.ru bash ~/blps/lab1_deploy/start.sh
  ssh -p 2222 s"$USER"@se.ifmo.ru -L "$PORT":localhost:"$PORT"
else
  echo "Create $CONFIG before deploying"
fi