import sys

f = open("test.txt", "w+")
f.write("abcccc\n")
f.write("sys.argv\n")
f.close()
print(sys.argv)
print("test haaa")
