FROM node:16.16.0
WORKDIR yi
COPY package.json ./
ENV TZ=Asia/Seoul
RUN npm install -y tzdata
COPY ./ ./
EXPOSE 3000
CMD ["nginx", "-g","daemon off;"]
