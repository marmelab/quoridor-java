.PHONY: build
export UID = $(shell id -u)
export GID = $(shell id -g)

help: ## Print all commands (default)
	@grep -E '^[a-zA-Z0-9_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

clean: ## Clean the workspace
	docker-compose run web gradle clean

build: ## build server
	docker-compose run --rm web gradle assemble

run: ## build and start the server
	docker-compose up

test: ## Run the tests
	docker-compose run --rm web gradle check

coverage: ## Calculate the code coverage
	docker-compose run web gradle jacocoTestReport
