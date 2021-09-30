insert into user values(1,"ROLE_ADMIN" ,"userNickname1","userpassword1","imgaeUrl", "user1");
insert into user values(2,"ROLE_ADMIN" ,"userNickname2","userpassword2","imgaeUrl", "user2");


insert into board values(1, "코인잡담","글 내용1","2021-09-13 04:43:52","imageUrls","2021-09-13 04:43:52",1,"글제목",2,"userNickname1",1);
insert into board values(2, "자유게시판","글 내용2","2021-09-14 04:43:52","imageUrls","2021-09-14 04:43:52",5,"글제목",5,"userNickname1",1);
insert into board values(4, "코인잡담","글 내용3","2021-09-25 04:43:52","imageUrls","2021-09-13 04:43:52",10,"글제목",2,"userNickname1",1);
insert into board values(5, "자유게시판","글 내용4","2021-09-13 05:43:52","imageUrls","2021-09-14 04:43:52",110,"글제목",10,"userNickname2",2);
insert into board values(6, "자유게시판","글 내용5","2021-09-12 05:43:52","imageUrls","2021-09-14 04:43:52",222,"글제목",4,"userNickname2",2);


insert into comment values(1, "댓글내용1","2021-09-16 13:43:52","2021-09-19 04:43:52",0,"댓글작성자1",1);
insert into comment values(2, "댓글내용2","2021-09-16 01:43:52","2021-09-16 04:43:52",1,"댓글작성자2",1);

insert into comment values(3, "댓글내용3","2021-09-17 12:43:52","2021-09-19 04:43:52",2,"댓글작성자3",2);
insert into comment values(4, "댓글내용4","2021-09-15 04:43:52","2021-09-20 04:43:52",0,"댓글작성자4",2);

insert into comment values(5, "댓글내용5","2021-09-16 04:43:52","2021-09-19 04:43:52",1,"댓글작성자5",4);
insert into comment values(6, "댓글내용6","2021-09-15 05:43:52","2021-09-16 04:43:52",2,"댓글작성자6",4);

insert into comment values(7, "댓글내용7","2021-09-17 06:43:52","2021-09-19 04:43:52",1,"댓글작성자1",4);
insert into comment values(8, "댓글내용8","2021-09-19 05:43:52","2021-09-20 04:43:52",0,"댓글작성자2",4);


insert into reply values(1,"대댓글작성자1", "대댓글내용1","2021-09-20 04:43:52","2021-09-19 04:43:52",1);
insert into reply values(2, "댓글작성자2","대댓글내용2","2021-09-20 12:43:52","2021-09-12 04:43:52",1);

insert into reply values(3,"대댓글작성자1", "대댓글내용1","2021-09-21 04:43:52","2021-09-22 04:43:52",2);
insert into reply values(4,"대댓글작성자2", "대댓글내용2","2021-09-21 12:43:52","2021-09-22 04:43:52",2);

insert into reply values(5,"대댓글작성자1", "대댓글내용1","2021-09-20 04:43:52","2021-09-19 04:43:52",3);
insert into reply values(6,"대댓글작성자2", "대댓글내용2","2021-09-20 12:43:52","2021-09-12 04:43:52",3);

insert into reply values(7,"대댓글작성자1", "대댓글내용1","2021-09-21 04:43:52","2021-09-22 04:43:52",4);
insert into reply values(8, "대댓글작성자2","대댓글내용2","2021-09-21 12:43:52","2021-09-22 04:43:52",4);