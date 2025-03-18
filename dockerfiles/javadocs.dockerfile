FROM node

WORKDIR /app 

COPY target/reports/apidocs ./docs

RUN yarn add light-server

EXPOSE 3000

CMD ["npx", "light-server", "-s", "./docs", "-p", "3000", "-w", "**/*.*"]