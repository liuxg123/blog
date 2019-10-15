From nginx
RUN rm /etc/nginx/conf.d/default.conf
#ADD 
default.conf /etc/nginx/conf.d/
#RUN 
rm /etc/nginx/nginx.conf
COPY . /usr/share/nginx/html/  
COPY mysite.template /etc/nginx
CMD  envsubst  '$HOST_IP' </etc/nginx/mysite.template >  /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'
