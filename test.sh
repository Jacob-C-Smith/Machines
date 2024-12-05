rm results.txt
for n in {1..1000}; 
do
    printf "%d\r" $n
    /usr/bin/time -f"%e" java tm 1> /dev/null 2>> results.txt
done