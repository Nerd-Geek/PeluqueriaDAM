FROM yobasystems/alpine-mariadb:10

ENV MYSQL_ROOT_PASSWORD password
ENV MYSQL_USER user
ENV MYSQL_PASSWORD password
ENV MYSQL_DATABASE peluqueriaDAM

COPY ./init /docker-entrypoint-initdb.d/
