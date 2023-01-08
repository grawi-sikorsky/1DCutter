#stage 1
FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

#stage 2
FROM nginx:latest
COPY --from=node /app/dist/onedcutter-front /usr/share/nginx/html
COPY /.docker/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80

RUN ["apt-get", "update"]
RUN ["apt-get", "-y", "install", "nano"]
