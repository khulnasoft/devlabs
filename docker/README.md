# Docker Images

This directory contains centralized Docker configurations for the project.

## Available Images

### `Dockerfile.react-weather-app`
React weather application used for Selenium testing demonstrations.

**Usage:**
```bash
cd docker/
docker build -f Dockerfile.react-weather-app -t react-weather-app .
docker run -p 3000:3000 react-weather-app
```

## Adding New Dockerfiles

1. Add your Dockerfile to this directory
2. Update this README with documentation
3. Reference the Dockerfile path in your project configurations

## Best Practices

- Use multi-stage builds when possible for smaller images
- Pin specific versions for base images (avoid `latest` tags)
- Document the purpose and usage of each Dockerfile
- Keep Dockerfiles simple and focused on single responsibilities
