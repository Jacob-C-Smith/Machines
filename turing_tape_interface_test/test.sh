mkdir $(printf "%s.%s" $1 $2) &> /dev/null

for i in $(seq 1 $1);
do
    java main $2 >> $(printf "%s/%s.%s.result" $(printf "%s.%s" $1 $2) $1 $2)
done

cd $(printf "%s.%s" $1 $2)

cat $(printf "%s.%s.result" $1 $2) | grep "Array List:" | awk -F ':' '{print $2}' | sort -n > al.res
cat $(printf "%s.%s.result" $1 $2) | grep "Array:"      | awk -F ':' '{print $2}' | sort -n > a.res
cat $(printf "%s.%s.result" $1 $2) | grep "Stack:"      | awk -F ':' '{print $2}' | sort -n > s.res
cat $(printf "%s.%s.result" $1 $2) | grep "Vector:"     | awk -F ':' '{print $2}' | sort -n > v.res

printf " === $1 $2 Array List ===\n %s\n" $(cat al.res | ../stats); 
printf " === $1 $2 Array ===\n %s\n" $(cat a.res |  ../stats);
printf " === $1 $2 Stack ===\n %s\n" $(cat s.res |  ../stats);
printf " === $1 $2 Vector ===\n %s\n" $(cat v.res |  ../stats);

cd - &> /dev/null