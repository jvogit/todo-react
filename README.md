# spring-react-nextjs
Fullstack web application template. Uses: Springboot, React on nextjs,
GraphQL, Postgres, and JWT authentication. Inspired by [this YouTube video](https://www.youtube.com/watch?v=I6ypD7qv3Z8).
# docker deployment
Once again Docker!!! Using docker-compose to run multicontainers.
- Read each README in `client` and `server`
- Set environment variables directly in the `docker-compose.yml`
- Run `docker-compose up --build`
- Find service at `http://localhost:3000`
# AWS Deployment
Deploys application on AWS stack: VPC + Load balancer + ECS on EC2 + RDS on Postgres.
Charges will incur for AWS resources.
- Follow instructions in CDK package to deploy stack
# Heroku + Vercel deployment
Backend service on heroku with frontend service on Vercel. Also it's free*!
## Push server to Heroku
Using Heroku CLI or Heroku website.

The following is steps for invoking the `setup` portion of heroku.yml
- `heroku update beta`
- `heroku plugins:install @heroku-cli/plugin-manifest` (if not already installed)
- `heroku create <your-app-name> --manifest`

Othwerise, please follow
- Set up heroku dyno on website or thru cli
- Install postgres addon on website or thru cli
- `heroku stack:set container` (let heroku know this is a Docker contianer stack)

Now follow:
- `heroku git:remote -a <appname>` (to set heroku remote in your local repo)
- `heroku config:set PGSSLMODE=require` (use SSL for postgres)
- `heroku config:set ACCESS_TOKEN_SECRET=<secret here>`
- `heroku config:set REFRESH_TOKEN_SECRET=<secret here>`
- `heroku config:set JWT_ISSUER=<issuer name>`
- `heroku config:set CORS_ORIGIN=<value>` (Vercel link, domain name, etc. For now put `http://localhost:3000`)
- `git push heroku master`
## Push to vercel
In client package
- `yarn vercel` (follow instructions!)
- Inspect vercel app in `preview` stage. Then set environment variable `NEXT_PUBLIC_BASE_API_URL` with heroku backend url
- `yarn vercel --prod`
- Remember to set `CORS_ORIGIN` in Heroku to vercel app url.
## Pitfalls
- Take care to not have trailing slash in URLs when settins `CORS` and `NEXT_PUBLIC_BASE_API_URL`