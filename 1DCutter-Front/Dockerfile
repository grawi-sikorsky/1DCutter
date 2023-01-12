#stage 1 prod
FROM node:slim as node
WORKDIR /app
COPY . .
RUN npm install --prefer-offline
RUN npm run build --prod

# stage 2
# FROM nginx:latest
# COPY --from=node /app/dist/onedcutter-front /usr/share/nginx/html
# COPY /.docker/nginx.conf /etc/nginx/conf.d/default.conf
# EXPOSE 80

# RUN ["apt-get", "update"]
# RUN ["apt-get", "-y", "install", "nano"]


