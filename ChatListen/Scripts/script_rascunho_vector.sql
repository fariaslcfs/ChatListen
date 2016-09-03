use fbchatwordlog;
show tables;
drop table if exists vectorNPS;
create table vector
				(
					id int not null auto_increment primary key,  
                    posit decimal(5,4),
                    posw decimal(5,4),
                    negw decimal(5,4),
                    joyw decimal(5,4),
                    sadw decimal(5,4),
                    angw decimal(5,4),
                    surw decimal(5,4),
                    disw decimal(5,4),
                    feaw decimal(5,4),
                    appw decimal(5,4),
                    relw decimal(5,4),
                    famw decimal(5,4),
                    cdew decimal(5,4),
                    infw decimal(5,4),
                    prpw decimal(5,4),
                    refw decimal(5,4),
                    oblv decimal(5,4),
                    emot decimal(5,4),
                    imps decimal(5,4),
                    catg  decimal(5,4)
				);

select * from vectorNPS;
select * from vectorPred;
show tables;
drop table vector;
select * from messages;
truncate messages;

                