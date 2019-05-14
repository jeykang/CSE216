fun find(lst, idx) = 
	if idx = 0 then
		hd lst
	else
		find(tl lst, idx - 1);

fun replace(lst, idx, itm, frt) = 
	if idx = 0 then
		frt @ itm :: tl lst
	else
		replace(tl lst, idx - 1, itm, frt @ [hd lst]);

fun slice(lst, begin, stop, cur, fin) = 
	if cur < begin then
		slice(tl lst, begin, stop, cur + 1, fin)
	else
		if cur < stop then
			slice(tl lst, begin, stop, cur + 1, fin @ [hd lst])
		else
			fin;

fun merge(whole, left, right, k, i, j) = 
	if i < length left andalso j < length right then
		if find(left, i) < find(right, j) then
			merge(replace(whole, k, find(left, i), []), left, right, k + 1, i + 1, j)
		else
			merge(replace(whole, k, find(right, j), []), left, right, k + 1, i, j + 1)
	else
		if i < length left then
			merge(replace(whole, k, find(left, i), []), left, right, k + 1, i + 1, j)
		else
			if j < length right then
				merge(replace(whole, k, find(right, j), []), left, right, k + 1, i, j + 1)
			else
				whole;

fun mergeSort(num) = 
	if length num > 1 then
		merge(num, mergeSort(slice(num, 0, length num div 2, 0, [])), mergeSort(slice(num, length num div 2, length num, 0, [])), 0, 0, 0)
	else
		num;
			