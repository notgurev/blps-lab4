.PHONY: build helios run clean dev prod build

all:
	@echo "Тут возможно что-то не работает, я просто скопировал это из старого проекта"

helios:
	ssh -p2222 -Y -L 5432:pg:5432 s286528@se.ifmo.ru

build:
	./mvnw clean package

clean:
	rm -rf target

run:
	java -jar target/blps.jar

deploy:
	scp -P 2222 ./target/blps.jar s286528@se.ifmo.ru:.