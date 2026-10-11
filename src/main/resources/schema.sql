create table campaigns (
    id bigint primary key,
    start_time timestamp with time zone not null,
    end_time timestamp with time zone,
    status varchar(32) not null
);

create index idx_campaigns_running_window
    on campaigns (status, start_time, end_time);
