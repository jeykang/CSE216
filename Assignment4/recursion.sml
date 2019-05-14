fun reverse([]) = []
| reverse(lst) = reverse(tl lst) @ [hd lst];

fun compress([a]) = [a]
| compress(lst) = 
	if hd lst = hd (tl lst) then 
		compress(tl lst) 
	else
		[hd lst] @ compress(tl lst);

fun cluster(lst, []) = cluster(tl lst, [[hd lst]])
| cluster([], acc) = acc
| cluster(lst, acc) = 
	if hd lst = hd (hd acc) then
		cluster(tl lst, [hd lst::hd acc] @ tl acc)
	else
		cluster(tl lst, [[hd lst]]@acc);

fun sumlists(n, m) = 
	if length n = 0 then 
		m 
	else 
		if length m = 0 then 
			n 
		else 
			[hd n + hd m] @ sumlists(tl n, tl m);

fun flatten([]) = []
| flatten(x) = hd x @ flatten(tl x);