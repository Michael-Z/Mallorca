public class FlopNN {
private static double[] layerSizeArray = new double[3];
static {
layerSizeArray[0] = 13;
layerSizeArray[1] = 26;
layerSizeArray[2] = 3;
}
private static double[] weights = new double[] {-468.97786440106546, 
-72.0645339490218, 193.96979375152313, 173.63360035302273, 42.33095924511223, 120.22942985497573, -139.08704962421785, 98.00293125902535, 56.72890376111885, 42.12974227071153, -78.46233190188259, 
-91.86725935338522, -41.59729566968004, 31.309692601487207, -19.375472797235417, 30.429466653879967, 34.75959202673831, 10.895225303418378, -86.05135410673991, 28.811213521531787, 27.68983890900749, 
-3.246610421576916, -35.98679903002852, 0.7147430110909478, -37.50641083545134, 57.42316131807304, 106.44839283752485, -47.64610111095967, 143.03106349463835, -165.2746187465993, -50.125251694612714, 
-496.5964241211939, 274.5911374619307, -156.5936046383855, -98.90257969298496, -65.64356868288603, 20.237379271065716, -1.0180572152247052, 0.02476675524698651, 280.02569858876444, 25.88049795371138, 
47.56245005767562, 173.76050592840096, 20.725703612648775, 258.5148202750157, 196.40308421449822, -100.84167831370425, 41.48266804332799, 39.51225684934626, 71.93339404876612, 122.52698083354059, 
212.38488036294729, -67.50599058202009, -12.322251709448294, 110.15358267757934, 43.68180407746823, 57.781197938506025, -33.107015099970845, 3.9572085117323987, -130.1145591585195, 197.31454435069404, 
27.69068277974839, -64.31455884499911, -69.19158448402565, -106.28676294035219, 283.73192308890873, -250.9662975750104, -4.083025498691597, 54.42889215768348, 6.986315380348198, -371.3136626071811, 
24.671824504641844, -13.46113273965438, 3.3756437891633806, -109.25361046257882, -29.514918254371086, -127.83251033196343, 87.49145594388301, -3.771035453442569, 2.690279705825428, 4.420696717270998, 
21.38247206115165, -17.258696147024622, 6.366001744737577, -6.002097296185069, -18.787001705776632, 12.139526882502581, -54.65093203099351, -7.502025373683643, 3.9802138412832697, -6.726298273724783, 
407.59678943634424, -46.73735043493632, -349.3536666642922, 396.13968270726497, 384.3238104385241, 485.0577926639665, -301.960677461821, 344.65909955515735, 72.44110431004975, 104.92175652886844, 
101.57067696794886, 92.92948973474594, 218.91432271400652, 232.68183824034324, 41.07058957646674, 85.71813048317566, 231.74919552559706, 94.85913355017964, 322.4106594658298, 169.95196312109374, 
-22.75686763942048, 54.26566145716394, 40.38158737888783, 62.354926685515984, 100.97585705653604, 212.43531005780548, 296.7984791746534, 30.775516765973794, -179.0874331029028, 254.1547440806913, 
127.00365984709576, 322.019464327835, 2.018030807046803, 28.35771837103107, 23.103016931103568, 73.38971239415642, 83.70283493942735, 146.5590550399082, 211.4925918984611, 84.4459748754929, 
5.791158870066695, -0.9744460183660677, 1.279561178072002, -0.6869846766407579, 1.244738869402933, 0.018970151873418006, -8.680901842597573, -29.67339247199443, 14.885166686620305, 43.932947270678824, 
43.82134941019875, -65.99193897621856, 168.21297449101314, 64.54922045147809, -235.92741779010493, 501.4617177705238, 471.7790320387568, 367.40874417231356, -146.40857627200867, 361.8826436084268, 
-36.72739411937694, 83.19879458255757, 83.08434210592803, 115.34478085819742, 212.03889581328363, 259.67484271735225, 33.80395786959999, 8.255777807932114, 226.60249056266244, 91.52109338231591, 
292.04819114076275, 138.5149556416445, -25.478073545406705, 35.892923567382354, 47.338523418921504, 71.48588687312655, 118.77921278285582, 212.28665720401048, 212.14302269330543, 53.483948362283876, 
38.612826374351265, 283.4925548779847, 157.2096142498775, 410.37177211863263, 84.16354841813418, 56.177390876054375, 71.03426067127603, 54.321053597805104, 57.560747275286666, 93.38363544288958, 
211.90671255088637, 225.23955658392637, 46.48890446601249, 104.91950949222594, 230.11833403271584, 85.82919618782323, 372.20912473544183, 160.79998287637198, -22.352987227574186, 77.11469576821735, 
42.42168015745175, 56.07442549012611, 92.4784065662127, 211.94686833827942, 165.38358333377425, 64.827064526092, -99.23902465264308, 437.1367649811327, 378.1719252402078, 390.11055833598067, 
-48.56557554530994, 267.77338975891047, 4.467494901933898, 69.18755146158479, 69.56742497761077, 97.84368718951684, 212.46245630891244, 441.06710419639734, 81.82805117557133, -194.22195775828868, 
69.52812082375722, -109.74261454944622, -261.0970887767091, 264.33088819149623, -242.82743061980705, -127.92318024309682, 29.95979493511644, 176.65526180494024, 240.90234853839263, 233.22806239906578, 
-84.95777840672892, 147.4500204157648, 235.12985729432216, 665.0291307016291, 649.3222451552311, 809.4166990873354, -115.83864556489696, 576.6899530392704, 141.96212955888797, 67.220921975831, 
-1.9133605077923082, -38.7090086147547, 209.8646193707342, -55.91498894164998, 10.66595517777968, 519.800456390463, 221.8858647971416, 201.5499705364478, 116.86791694057872, 339.0098127621919, 
125.89733777473023, -52.81131767763971, -50.31469121519574, 15.559964870541043, -45.32491911559242, 160.02930010303163, 18.429053008091653, 0.9784729035123015, -2.407229032743225, 0.6793734520303769, 
-3.3945809453952758, 1.7020490705213334, -0.3701349281902159, 0.3016572585534626, 3.852826413120102, -62.67076583253965, 7.589307509661386, -22.19668559870047, 5.518917885032391, 121.19432791625165, 
24.385830843702074, 1027.9214494907226, 70.60945008818203, -1.481984905601287, 75.5935313779554, 824.3234321181264, -242.34176978062433, 165.4562415165427, -117.47694101477401, 52.90252836589354, 
-72.08288761338271, 214.31629826335856, -412.55449902971935, -30.05476386591179, 85.99958128423948, -18.054477236970182, 1.5802005585197825, 89.86818323705279, -37.72351931927852, 38.1651798413556, 
133.65735927120548, -27.76089618497315, -61.24976502646173, -107.80822764422139, 62.104762063477224, -5.618329621048603, -240.3093054603409, 141.68944493872382, 69.23942309791182, 72.31210799375768, 
-462.01749315851447, 12.967914587466176, -72.34938962876207, 14.252918020836944, -312.5558991313667, 27.677142227051093, 24.657838054774437, 380.9129773035298, -8.941451049209789, -0.53852271293285, 
9.341683277269954, 0.7573425694603909, 0.1016693059267817, -11.954305985898923, -6.615475867469213, 3.517763495947699, -8.96994880223121, -99.43674794902161, 126.02199708586997, -18.430522069182434, 
18.726412503201317, 16.86244374035235, -11.074329153650966, -6.6964375292911305, -26.809218928837325, 12.17418889447748, 9.357419910164962, 15.069471411776032, -15.718113125527028, 36.80326580100133, 
-52.50850580624192, -18.319529139846424, -55.724014369574064, 2.4974682091050666, 3.8915734657457666, -0.43037794292692233, -7.231384243787442, 0.7802723180824264, 2.0969736084696744, -7.81563638415399, 
-1.674427051256958, 0.10872320803000418, 0.9213494846899412, 1.2972592554965006, -2.2425724160420155, -1.6191500927160043, 0.6650377312890311, 1.8258282886769088, 1.7947203245579841, -0.5777751754138333, 
-1.8330466121490134, 2.522230049301166, -3.1680228083421436, -2.64976539428396, -5.892798063907591, 1.0127684791680642, -6.771556052732284, -5.602457561896634, -5.5793727037936955, 0.21132429967634275, 
0.7415240601844986, 0.9734787883589893, -0.653104208648065, -1.9618613594119447, 0.15134419608400865, 2.6715477256215543, 0.5938483830856116, 0.6093620426529399, -1.4671594090641227, -2.199721489129017, 
-1.7521982284603639, -0.1981187514713725, -1.7034210756436385, -1.4004378419316232, -1.5557186417833746, -0.39111918915276733, -1.352793558708897, 1.7516359673011008, 2.5382242648307254, 2.2496703589053393, 
2.53564567988234, 1.357444086056463, 1.1507194288911486, 2.2870497938949663, 1.7192722278327037, -0.8411347911149999, -0.247122473194464, -0.8864086220793641, -0.0029706434265902434, 0.08297970012967829, 
-1.0360466728544708, -2.632740732386588, -0.3061448894698974, 2.5487674774540507, -0.014593440695614258, 1.289013728819127, 2.460354392619582, 1.283546016546468, 0.29275883812128045, 0.40378242424264743, 
0.043089830669767834, 0.7765278958397192, 0.36410994427516596, -0.04147824637977497, -3.287047487525235, 1.066526292648299, -4.679804782178381, -1.2845930490353614, -1.1212774973782442, -0.05606089584028304, 
-1.4551464027238648, -0.21091926537098443};
}