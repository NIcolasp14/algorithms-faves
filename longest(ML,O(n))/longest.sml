(*  Στην παρακάτω υλοποίηση, έχουμε βασιστεί στον αλγόριθμο της παρακάτω σελίδας, τον οποίο τροποποιήσαμε κατάλληλα,
καθώς δεν κάλυπτε την περίπτωση (στο δικό μας πρόβλημα) η "καλή" περίοδος να συμπεριλαμβάνει την πρώτη ή την τελευταία μέρα.
https://www.geeksforgeeks.org/longest-subarray-having-average-greater-than-or-equal-to-x-set-2/  *)

fun longest file =
let
fun parse file =
    let
          (* A function to read an integer from specified input. *)
        fun readInt input =
          Option.valOf (TextIO.scanStream (Int.scan StringCvt.DEC) input)

      	(* Open input file. *)
      	val inStream = TextIO.openIn file

        (* Read an integer (number of countries) and consume newline. *)
      	val days = readInt inStream
        val hosp = readInt inStream

        (* Read next line*)
       	val _ = TextIO.inputLine inStream

    (* A function to read N integers from the open file. *)
      fun readInts 0 acc = acc (* Replace with 'rev acc' for proper order. *)
        | readInts i acc = readInts (i - 1) (readInt inStream :: acc)

    (*to read ints*)
    val pin=[]
    val list = readInts days pin
    (*val array = Array.fromList(list)*)

    in
   	   	(days, hosp, list) (*array*)
    end

(* solver *)
fun find_Lmin ([], l_m, min) = l_m
  | find_Lmin (x::xs, l_m, min) = if x<min then find_Lmin(xs, x::l_m, x)
                                  else find_Lmin(xs, min::l_m, min)

fun find_Rmax ([], r_m, max) = r_m
  | find_Rmax (x::xs, r_m, max) = if x>max then find_Rmax(xs, x::r_m, x)
                                  else find_Rmax(xs, max::r_m, max)

fun prefixArr ([], p_l, sum) = p_l
  | prefixArr (x::xs, p_l, sum) = prefixArr(xs, (x+sum)::p_l, sum+x)

  fun newArr ([],modified,noso)=modified
    | newArr ((x::lista),modified,noso)= newArr (lista,(~x-noso)::modified,noso)

fun while1 (l::left, r::right, maxdays, days, i, j) = if(i<days andalso j<days-1) then
                                                    let
                                                          fun check a b = if a<b then true
                                                                          else false
                                                          fun diasthma (i, j, left, right, a, b) = if check a b then (i, j+1, a::left, right)
                                                                                                    else (i+1, j, left, b::right)
                                                          val (inew,jnew,newleft,newright) = diasthma(i,j,left,right,l,r)
                                                          val subarray=jnew-inew
                                                          fun findmax (a,b)=if a>b then a
                                                                            else b
                                                     in
                                                          while1 (newleft,newright,findmax(maxdays,subarray),days,inew,jnew)
                                                     end
                                                     else maxdays
  fun globalmax (a,b)=if a>b then a
                      else b

                      fun check_first_el([], counter, result1) = result1
                       |  check_first_el (x::xs, counter, result1) = if (x >= 0) then check_first_el (xs, counter+1, counter+1)
                                                                                     else check_first_el (xs, counter+1, result1)
                    (*  fun check_last_el (xs, [], days, hosp, counter, result2) = result2
                       | check_last_el (x::xs, y::ys, days, hosp, counter, result2) = if (x < y) then check_last_el (xs, ys, days, hosp, counter+1, counter+1)
                                                                                     else check_last_el (xs, ys, days, hosp, counter+1, result2)*)
                     fun solve (days,hosp,list) =
                       let
                           val modlist = newArr(list, [], hosp)
                           (*den xreiazetai list.rev modlist exei ftiaxtei anapoda h prwth list apo to arxeio*)
                           val prefix_list = prefixArr (modlist, [], 0)(*anapoda*)
                           val suffix = prefixArr(List.rev modlist, [], 0)

                           (*Gia ton Rmax theloyme na diasxisoyme thn prefix_list apo to telos pros thn arxh*)
                           val rmax = find_Rmax(prefix_list, [], hd prefix_list)
                           (*Prefix_list me thn swsth seira*)
                           val plordered = List.rev (prefix_list)
                           (*exei kataskeyasthei anapoda, gia thn Lmin ksekiname apo thn arxh*)
                           val lmin = find_Lmin(plordered, [], hd plordered)
                           (*Ftiaxnetai anapoda, ara thn antistrefoume thn Lmin*)
                           val lminor = List.rev (lmin)
                           (*Eksetazoume ta test cases poy xanoyme:Na ksekinaei to max subarray apo to prwto stoixeio*)
                           (*Alliws tha xaname ena stoixeiosth metrhsh*)
                           (* is reversed from parse*)
                           val first_element = check_first_el (plordered, 0, 0)
                           val last_element = check_first_el (List.rev suffix, 0, 0)
                           val pre_result = globalmax (first_element, last_element)
                           val result = globalmax(while1(lminor, rmax, 0, days, 1, 0), pre_result)
                         in
                           result
                         end
                         val methh = solve(parse file)
in
   print(Int.toString methh ^ "\n")
end
 
