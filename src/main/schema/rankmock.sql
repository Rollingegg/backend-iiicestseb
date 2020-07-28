insert into affiliation (id, name)
values (-1, 'aff1'),
       (-2, 'aff2'),
       (-3, 'aff3');
insert into author (id, name, first_name, last_name, affiliation_id)
values (-1, 'name1', 'first1', 'last1', -1),
       (-2, 'name2', 'first2', 'last2', -2),
       (-3, 'name3', 'first3', 'last3', -3);
insert into author_page_rank (author_id, page_rank)
values (-1, 0.33),
       (-2, 0.13),
       (-3, 0.43);
insert into author_statistics (author_id, h_index, g_index, avg_cite, paper_num, ase_paper_num, icse_paper_num)
VALUES (-1, 1, 1, 1, 1, 1, 0),
       (-2, 2, 2, 2, 2, 2, 0),
       (-3, 3, 3, 3, 2, 1, 1);
