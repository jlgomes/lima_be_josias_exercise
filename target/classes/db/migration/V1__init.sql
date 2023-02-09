create table role
(
    id   varchar(255) not null
        primary key,
    name varchar(255) not null
);

insert into role(id, name)
values ('1b3c333b-36e7-4b64-aa15-c22ed5908ce4', 'Developer');
insert into role(id, name)
values ('25bbb7d2-26f3-11ec-9621-0242ac130002', 'Product Owner');
insert into role(id, name)
values ('37969e22-26f3-11ec-9621-0242ac130002', 'Tester');

create table user
(
    id   varchar(255) not null
        primary key,
    first_name varchar(255),
    last_name varchar(255),
    display_name varchar(255),
    avatar_url varchar(255),
    location varchar(255)
);

insert into user(id, display_name)
values ('fd282131-d8aa-4819-b0c8-d9e0bfb1b75c', 'gianniWehner');

create table team
(
    id   varchar(255) not null
        primary key,
    name varchar(255) not null unique,
    team_lead_id varchar(255),
    constraint FK53w0pjjewq21saswq
        foreign key (team_lead_id) references user (id)
);

insert into team(id, name, team_lead_id)
values ('4c5a99a1-48ab-4654-8692-dbd41eb2714b', 'System Team', 'fd282131-d8aa-4819-b0c8-d9e0bfb1b75c');

create table team_members
(
    id   varchar(255) not null
        primary key,
    team_id varchar(255) not null,
    team_member_id varchar(255) not null,
    constraint FK53w0pjjvwqeghgqwheg2
        foreign key (team_id) references team (id),
    constraint FK53w0pnbj23h13hg3123
        foreign key (team_member_id) references user (id)
);

create table membership
(
    id   varchar(255) not null
        primary key,
    team_id varchar(255)  not null,
    user_id varchar(255)  not null,
    role_id varchar(255) not null,
    constraint UKovs2w4ph57xdtsrc5y3iqjvh1
        unique (role_id, team_id, user_id),
    constraint FK53w0pjjpc5me9htly7j74ym1s
        foreign key (team_id) references team (id),
    constraint FK53w0pjjpc5me9htlyeweqwe
        foreign key (user_id) references user (id),
    constraint FK53w0pjjpc5me9htly7sad2ed
        foreign key (role_id) references role (id)
);
