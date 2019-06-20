.PHONY: build
export UID = $(shell id -u)
export GID = $(shell id -g)

help: ## Print all commands (default)
	@grep -E '^[a-zA-Z0-9_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

clean: ## Clean the workspace
	docker-compose run web gradle clean

build: ## build server
	docker-compose up

start: ## start the server
	docker-compose run web gradle bootRun

run: ## build and start the server
	docker-compose run web gradle build bootRun

test: ## Run the tests
	docker-compose run web gradle test

coverage: ## Calculate the code coverage
	docker-compose run web gradle jacocoTestReport
