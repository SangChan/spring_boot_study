if [ -e "pid.txt" ]
then
	PID=$(<"pid.txt")
	kill -9 $PID
	rm "pid.txt"
fi
