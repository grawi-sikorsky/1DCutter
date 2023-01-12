#stage 1 prod
FROM node:slim as node
WORKDIR /app
COPY . .
RUN npm install --prefer-offline
RUN npm run build --prod

# stage 2
FROM nginx:latest
COPY --from=node /app/dist/onedcutter-front /usr/share/nginx/html
