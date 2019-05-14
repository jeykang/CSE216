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

fun partition(lst, low, high, start) = 
	if low < high then
		if find(lst, low) <= find(lst, high) then
			let
				val temp = start + 1
				val swap = find(lst, low)
				val interlist = replace(lst, low, find(lst, temp), nil)
			in
				partition(replace(interlist, temp, swap, nil), low + 1, high, temp)
			end
		else
			partition(lst, low + 1, high, start)
	else
		let		
			val temp = find(lst, start + 1)
			val interlist = replace(lst, start + 1, find(lst, high), nil)
		in
			(start + 1, replace(interlist, high, temp, nil))
		end;

fun quickSort(lst, low, high) =
	if low < high then
		let
			val temp = partition(lst, low, high, low - 1)
			val pi = #1 temp
			val leftsorted = quickSort(#2 temp, low, pi - 1)
		in
			
			quickSort(leftsorted, pi + 1, high)
		end
	else
		lst;

			
			
			