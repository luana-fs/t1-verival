# Variables
IMAGE_NAME = estacionamento-devcontainer
CONTAINER_CLI = npx -y @devcontainers/cli

.PHONY: all setup build up run compile shell down clean

all: build up run

# Step 1: Install the official devcontainer CLI tool if not present
setup:
	@echo "Checking for Node.js and Docker requirements..."
	@node -v > /dev/null 2>&1 || (echo "Error: Node.js is required to run the devcontainer CLI." && exit 1)
	@docker -v > /dev/null 2>&1 || (echo "Error: Docker is required to build containers." && exit 1)

# Step 2: Build the container image using the .devcontainer configuration
build: setup
	@echo "Building devcontainer..."
	$(CONTAINER_CLI) build --workspace-folder .

# Step 3: Spin up the container in the background
up:
	@echo "Starting devcontainer environment..."
	$(CONTAINER_CLI) up --workspace-folder .

# Step 4: Compile the Java application using Maven inside the container
compile: up
	@echo "Compiling project inside the container..."
	$(CONTAINER_CLI) exec --workspace-folder . mvn clean package

# Step 5: Execute the main Java application inside the container
run: compile
	@echo "Running the Estacionamento application inside the container..."
	$(CONTAINER_CLI) exec --workspace-folder . mvn exec:java -Dexec.mainClass="br.pucrs.vv.estacionamento.App"

# Open an interactive Bash shell inside the container environment
shell: up
	@echo "Opening bash shell inside the container..."
	$(CONTAINER_CLI) exec --workspace-folder . bash

# Stop and remove the active devcontainer environment
down:
	@echo "Stopping devcontainer..."
	@docker ps -q --filter label=devcontainer.local_folder=$$(pwd) | xargs -r docker stop
	@echo "Container stopped."

# Clean up Maven build targets locally
clean:
	rm -rf target
