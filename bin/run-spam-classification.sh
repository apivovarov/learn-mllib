DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

spark-submit --master local[*] --class org.x4444.mllib.SpamClassification \
  $DIR/../target/scala-2.10/learn-mllib_2.10-1.0.0-SNAPSHOT.jar \
  $DIR/../files/spam.txt \
  $DIR/../files/ham.txt
