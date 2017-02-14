/*
 COPYRIGHT 2009 ESRI

 TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
 Unpublished material - all rights reserved under the
 Copyright Laws of the United States and applicable international
 laws, treaties, and conventions.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts and Legal Services Department
 380 New York Street
 Redlands, California, 92373
 USA

 email: contracts@esri.com
 */

if(!dojo._hasResource["esri.WKIDUnitConversion"]){dojo._hasResource["esri.WKIDUnitConversion"]=true;dojo.provide("esri.WKIDUnitConversion");esri.WKIDUnitConversion={values:[1,0.2011661949,0.3047997101815088,0.3048006096012192,0.3048,0.304797265,0.9143985307444408,20.11678249437587,0.9143984146160287,20.11676512155263,0.3047994715386762,0.91439523,50000,150000],2000:0,2001:0,2002:0,2003:0,2004:0,2005:0,2006:0,2007:0,2008:0,2009:0,2010:0,2011:0,2012:0,2013:0,2014:0,2015:0,2016:0,2017:0,2018:0,2019:0,2020:0,2021:0,2022:0,2023:0,2024:0,2025:0,2026:0,2027:0,2028:0,2029:0,2030:0,2031:0,2032:0,2033:0,2034:0,2035:0,2036:0,2037:0,2038:0,2039:0,2040:0,2041:0,2042:0,2043:0,2044:0,2045:0,2056:0,2057:0,2058:0,2059:0,2060:0,2061:0,2062:0,2063:0,2064:0,2065:0,2066:1,2067:0,2068:0,2069:0,2070:0,2071:0,2072:0,2073:0,2074:0,2075:0,2076:0,2077:0,2078:0,2079:0,2080:0,2081:0,2082:0,2083:0,2084:0,2085:0,2086:0,2087:0,2088:0,2089:0,2090:0,2091:0,2092:0,2093:0,2094:0,2095:0,2096:0,2097:0,2098:0,2099:0,2100:0,2101:0,2102:0,2103:0,2104:0,2105:0,2106:0,2107:0,2108:0,2109:0,2110:0,2111:0,2112:0,2113:0,2114:0,2115:0,2116:0,2117:0,2118:0,2119:0,2120:0,2121:0,2122:0,2123:0,2124:0,2125:0,2126:0,2127:0,2128:0,2129:0,2130:0,2131:0,2132:0,2133:0,2134:0,2135:0,2136:2,2137:0,2138:0,2139:0,2140:0,2141:0,2142:0,2143:0,2144:0,2145:0,2146:0,2147:0,2148:0,2149:0,2150:0,2151:0,2152:0,2153:0,2154:0,2155:3,2157:0,2158:0,2159:2,2160:2,2161:0,2162:0,2163:0,2164:0,2165:0,2166:0,2167:0,2168:0,2169:0,2170:0,2172:0,2173:0,2174:0,2175:0,2176:0,2177:0,2178:0,2179:0,2180:0,2181:0,2182:0,2183:0,2184:0,2185:0,2186:0,2187:0,2188:0,2189:0,2190:0,2192:0,2193:0,2195:0,2196:0,2197:0,2198:0,2200:0,2201:0,2202:0,2203:0,2204:3,2205:0,2206:0,2207:0,2208:0,2209:0,2210:0,2211:0,2212:0,2213:0,2214:0,2215:0,2216:0,2217:0,2219:0,2220:0,2222:4,2223:4,2224:4,2225:3,2226:3,2227:3,2228:3,2229:3,2230:3,2231:3,2232:3,2233:3,2234:3,2235:3,2236:3,2237:3,2238:3,2239:3,2240:3,2241:3,2242:3,2243:3,2244:3,2245:3,2246:3,2247:3,2248:3,2249:3,2250:3,2251:4,2252:4,2253:4,2254:3,2255:3,2256:4,2257:3,2258:3,2259:3,2260:3,2261:3,2262:3,2263:3,2264:3,2265:4,2266:4,2267:3,2268:3,2269:4,2270:4,2271:3,2272:3,2273:4,2274:3,2275:3,2276:3,2277:3,2278:3,2279:3,2280:4,2281:4,2282:4,2283:3,2284:3,2285:3,2286:3,2287:3,2288:3,2289:3,2290:0,2291:0,2292:0,2294:0,2295:0,2308:0,2309:0,2310:0,2311:0,2312:0,2313:0,2314:5,2315:0,2316:0,2317:0,2318:0,2319:0,2320:0,2321:0,2322:0,2323:0,2324:0,2325:0,2326:0,2327:0,2328:0,2329:0,2330:0,2331:0,2332:0,2333:0,2334:0,2335:0,2336:0,2337:0,2338:0,2339:0,2340:0,2341:0,2342:0,2343:0,2344:0,2345:0,2346:0,2347:0,2348:0,2349:0,2350:0,2351:0,2352:0,2353:0,2354:0,2355:0,2356:0,2357:0,2358:0,2359:0,2360:0,2361:0,2362:0,2363:0,2364:0,2365:0,2366:0,2367:0,2368:0,2369:0,2370:0,2371:0,2372:0,2373:0,2374:0,2375:0,2376:0,2377:0,2378:0,2379:0,2380:0,2381:0,2382:0,2383:0,2384:0,2385:0,2386:0,2387:0,2388:0,2389:0,2390:0,2391:0,2392:0,2393:0,2394:0,2395:0,2396:0,2397:0,2398:0,2399:0,2400:0,2401:0,2402:0,2403:0,2404:0,2405:0,2406:0,2407:0,2408:0,2409:0,2410:0,2411:0,2412:0,2413:0,2414:0,2415:0,2416:0,2417:0,2418:0,2419:0,2420:0,2421:0,2422:0,2423:0,2424:0,2425:0,2426:0,2427:0,2428:0,2429:0,2430:0,2431:0,2432:0,2433:0,2434:0,2435:0,2436:0,2437:0,2438:0,2439:0,2440:0,2441:0,2442:0,2443:0,2444:0,2445:0,2446:0,2447:0,2448:0,2449:0,2450:0,2451:0,2452:0,2453:0,2454:0,2455:0,2456:0,2457:0,2458:0,2459:0,2460:0,2461:0,2462:0,2523:0,2524:0,2525:0,2526:0,2527:0,2528:0,2529:0,2530:0,2531:0,2532:0,2533:0,2534:0,2535:0,2536:0,2537:0,2538:0,2539:0,2540:0,2541:0,2542:0,2543:0,2544:0,2545:0,2546:0,2547:0,2548:0,2549:0,2550:0,2551:0,2552:0,2553:0,2554:0,2555:0,2556:0,2557:0,2558:0,2559:0,2560:0,2561:0,2562:0,2563:0,2564:0,2565:0,2566:0,2567:0,2568:0,2569:0,2570:0,2571:0,2572:0,2573:0,2574:0,2575:0,2576:0,2577:0,2578:0,2579:0,2580:0,2581:0,2582:0,2583:0,2584:0,2585:0,2586:0,2587:0,2588:0,2589:0,2590:0,2591:0,2592:0,2593:0,2594:0,2595:0,2596:0,2597:0,2598:0,2599:0,2600:0,2601:0,2602:0,2603:0,2604:0,2605:0,2606:0,2607:0,2608:0,2609:0,2610:0,2611:0,2612:0,2613:0,2614:0,2615:0,2616:0,2617:0,2618:0,2619:0,2620:0,2621:0,2622:0,2623:0,2624:0,2625:0,2626:0,2627:0,2628:0,2629:0,2630:0,2631:0,2632:0,2633:0,2634:0,2635:0,2636:0,2637:0,2638:0,2639:0,2640:0,2641:0,2642:0,2643:0,2644:0,2645:0,2646:0,2647:0,2648:0,2649:0,2650:0,2651:0,2652:0,2653:0,2654:0,2655:0,2656:0,2657:0,2658:0,2659:0,2660:0,2661:0,2662:0,2663:0,2664:0,2665:0,2666:0,2667:0,2668:0,2669:0,2670:0,2671:0,2672:0,2673:0,2674:0,2675:0,2676:0,2677:0,2678:0,2679:0,2680:0,2681:0,2682:0,2683:0,2684:0,2685:0,2686:0,2687:0,2688:0,2689:0,2690:0,2691:0,2692:0,2693:0,2694:0,2695:0,2696:0,2697:0,2698:0,2699:0,2700:0,2701:0,2702:0,2703:0,2704:0,2705:0,2706:0,2707:0,2708:0,2709:0,2710:0,2711:0,2712:0,2713:0,2714:0,2715:0,2716:0,2717:0,2718:0,2719:0,2720:0,2721:0,2722:0,2723:0,2724:0,2725:0,2726:0,2727:0,2728:0,2729:0,2730:0,2731:0,2732:0,2733:0,2734:0,2735:0,2736:0,2737:0,2738:0,2739:0,2740:0,2741:0,2742:0,2743:0,2744:0,2745:0,2746:0,2747:0,2748:0,2749:0,2750:0,2751:0,2752:0,2753:0,2754:0,2755:0,2756:0,2757:0,2758:0,2759:0,2760:0,2761:0,2762:0,2763:0,2764:0,2765:0,2766:0,2767:0,2768:0,2769:0,2770:0,2771:0,2772:0,2773:0,2774:0,2775:0,2776:0,2777:0,2778:0,2779:0,2780:0,2781:0,2782:0,2783:0,2784:0,2785:0,2786:0,2787:0,2788:0,2789:0,2790:0,2791:0,2792:0,2793:0,2794:0,2795:0,2796:0,2797:0,2798:0,2799:0,2800:0,2801:0,2802:0,2803:0,2804:0,2805:0,2806:0,2807:0,2808:0,2809:0,2810:0,2811:0,2812:0,2813:0,2814:0,2815:0,2816:0,2817:0,2818:0,2819:0,2820:0,2821:0,2822:0,2823:0,2824:0,2825:0,2826:0,2827:0,2828:0,2829:0,2830:0,2831:0,2832:0,2833:0,2834:0,2835:0,2836:0,2837:0,2838:0,2839:0,2840:0,2841:0,2842:0,2843:0,2844:0,2845:0,2846:0,2847:0,2848:0,2849:0,2850:0,2851:0,2852:0,2853:0,2854:0,2855:0,2856:0,2857:0,2858:0,2859:0,2860:0,2861:0,2862:0,2863:0,2864:0,2865:0,2866:0,2867:4,2868:4,2869:4,2870:3,2871:3,2872:3,2873:3,2874:3,2875:3,2876:3,2877:3,2878:3,2879:3,2880:3,2881:3,2882:3,2883:3,2884:3,2885:3,2886:3,2887:3,2888:3,2891:3,2892:3,2893:3,2894:3,2895:3,2896:4,2897:4,2898:4,2899:3,2900:3,2901:4,2902:3,2903:3,2904:3,2905:3,2906:3,2907:3,2908:3,2909:4,2910:4,2911:3,2912:3,2913:4,2914:4,2915:3,2916:3,2917:3,2918:3,2919:3,2920:3,2921:4,2922:4,2923:4,2924:3,2925:3,2926:3,2927:3,2928:3,2929:3,2930:3,2931:0,2932:0,2933:0,2935:0,2936:0,2937:0,2938:0,2939:0,2940:0,2941:0,2942:0,2943:0,2944:0,2945:0,2946:0,2947:0,2948:0,2949:0,2950:0,2951:0,2952:0,2953:0,2954:0,2955:0,2956:0,2957:0,2958:0,2959:0,2960:0,2961:0,2962:0,2964:3,2965:3,2966:3,2967:3,2968:3,2969:0,2970:0,2971:0,2972:0,2973:0,2975:0,2976:0,2977:0,2978:0,2979:0,2980:0,2981:0,2982:0,2984:0,2985:0,2986:0,2987:0,2988:0,2989:0,2991:0,2992:4,2993:0,2994:4,2995:0,2996:0,2997:0,2998:0,2999:0,3000:0,3001:0,3002:0,3003:0,3004:0,3005:0,3006:0,3007:0,3008:0,3009:0,3010:0,3011:0,3012:0,3013:0,3014:0,3015:0,3016:0,3017:0,3018:0,3019:0,3020:0,3021:0,3022:0,3023:0,3024:0,3025:0,3026:0,3027:0,3028:0,3029:0,3030:0,3031:0,3032:0,3033:0,3034:0,3035:0,3036:0,3037:0,3054:0,3055:0,3056:0,3057:0,3058:0,3059:0,3060:0,3061:0,3062:0,3063:0,3064:0,3065:0,3066:0,3067:0,3068:0,3069:0,3070:0,3071:0,3072:0,3073:0,3074:0,3075:0,3076:0,3077:0,3078:0,3079:0,3080:4,3081:0,3082:0,3083:0,3084:0,3085:0,3086:0,3087:0,3088:0,3089:3,3090:0,3091:3,3092:0,3093:0,3094:0,3095:0,3096:0,3097:0,3098:0,3099:0,3100:0,3101:0,3102:3,3106:0,3107:0,3108:0,3109:0,3110:0,3111:0,3112:0,3113:0,3114:0,3115:0,3116:0,3117:0,3118:0,3119:0,3120:0,3121:0,3122:0,3123:0,3124:0,3125:0,3126:0,3127:0,3128:0,3129:0,3130:0,3131:0,3132:0,3133:0,3134:0,3135:0,3136:0,3137:0,3138:0,3141:0,3142:0,3148:0,3149:0,3153:0,3154:0,3155:0,3156:0,3157:0,3158:0,3159:0,3160:0,3161:0,3162:0,3163:0,3164:0,3165:0,3166:0,3169:0,3170:0,3171:0,3172:0,3174:0,3175:0,3176:0,3177:0,3178:0,3179:0,3180:0,3181:0,3182:0,3183:0,3184:0,3185:0,3186:0,3187:0,3188:0,3189:0,3190:0,3191:0,3192:0,3193:0,3194:0,3195:0,3196:0,3197:0,3198:0,3199:0,3200:0,3201:0,3202:0,3203:0,3294:0,3296:0,3297:0,3298:0,3299:0,3300:0,3301:0,3302:0,3303:0,3304:0,3305:0,3306:0,3307:0,3308:0,3309:0,3310:0,3311:0,3312:0,3313:0,3314:0,3315:0,3316:0,3317:0,3318:0,3319:0,3320:0,3321:0,3322:0,3323:0,3324:0,3325:0,3326:0,3327:0,3328:0,3329:0,3330:0,3331:0,3332:0,3333:0,3334:0,3335:0,3336:0,3337:0,3338:0,3339:0,3340:0,3341:0,3342:0,3343:0,3344:0,3345:0,3346:0,3347:0,3348:0,3349:0,3350:0,3351:0,3352:0,3353:0,3354:0,3355:0,3356:0,3357:0,3358:0,3359:3,3360:0,3361:4,3362:0,3363:3,3364:0,3365:3,3366:5,3367:0,3368:0,3369:0,3370:0,3371:0,3372:0,3373:0,3374:0,3375:0,3376:0,3377:0,3378:0,3379:0,3380:0,3381:0,3382:0,3383:0,3384:0,3385:0,3386:0,3387:0,3388:0,3391:0,3392:0,3393:0,3394:0,3395:0,3396:0,3397:0,3398:0,3399:0,3400:0,3401:0,3402:0,3403:0,3404:3,3405:0,3406:0,3407:5,3408:0,3409:0,3410:0,3411:0,3412:0,3413:0,3414:0,3415:0,3416:0,3417:3,3418:3,3419:3,3420:3,3421:3,3422:3,3423:3,3424:3,3425:3,3426:3,3427:3,3428:3,3429:3,3430:3,3431:3,3432:3,3433:3,3434:3,3435:3,3436:3,3437:3,3438:3,3439:0,3440:0,3441:3,3442:3,3443:3,3444:3,3445:3,3446:3,3447:0,3448:0,3449:0,3450:0,3453:3,3456:3,3457:3,3458:3,3459:3,3460:0,3461:0,3462:0,3463:0,3464:0,3560:3,3561:3,3562:3,3563:3,3564:3,3565:3,3566:3,3567:3,3568:3,3569:3,3570:3,3571:0,3572:0,3573:0,3574:0,3575:0,3576:0,3577:0,3578:0,3579:0,3580:0,3581:0,3727:0,3734:3,3735:3,3736:3,3737:3,3738:3,3739:3,3753:3,3754:3,3755:3,3756:3,3757:3,3758:3,3759:3,3760:3,3761:0,3762:0,3763:0,3857:0,3920:0,3991:3,3992:3,20002:0,20003:0,20004:0,20005:0,20006:0,20007:0,20008:0,20009:0,20010:0,20011:0,20012:0,20013:0,20014:0,20015:0,20016:0,20017:0,20018:0,20019:0,20020:0,20021:0,20022:0,20023:0,20024:0,20025:0,20026:0,20027:0,20028:0,20029:0,20030:0,20031:0,20032:0,20062:0,20063:0,20064:0,20065:0,20066:0,20067:0,20068:0,20069:0,20070:0,20071:0,20072:0,20073:0,20074:0,20075:0,20076:0,20077:0,20078:0,20079:0,20080:0,20081:0,20082:0,20083:0,20084:0,20085:0,20086:0,20087:0,20088:0,20089:0,20090:0,20091:0,20092:0,20135:0,20136:0,20137:0,20138:0,20248:0,20249:0,20250:0,20251:0,20252:0,20253:0,20254:0,20255:0,20256:0,20257:0,20258:0,20348:0,20349:0,20350:0,20351:0,20352:0,20353:0,20354:0,20355:0,20356:0,20357:0,20358:0,20436:0,20437:0,20438:0,20439:0,20440:0,20499:0,20538:0,20539:0,20790:0,20822:0,20823:0,20824:0,20934:0,20935:0,20936:0,21035:0,21036:0,21037:0,21095:0,21096:0,21097:0,21148:0,21149:0,21150:0,21291:0,21292:0,21413:0,21414:0,21415:0,21416:0,21417:0,21418:0,21419:0,21420:0,21421:0,21422:0,21423:0,21473:0,21474:0,21475:0,21476:0,21477:0,21478:0,21479:0,21480:0,21481:0,21482:0,21483:0,21500:0,21780:0,21781:0,21817:0,21818:0,21891:0,21892:0,21893:0,21894:0,21896:0,21897:0,21898:0,21899:0,22032:0,22033:0,22091:0,22092:0,22171:0,22172:0,22173:0,22174:0,22175:0,22176:0,22177:0,22181:0,22182:0,22183:0,22184:0,22185:0,22186:0,22187:0,22191:0,22192:0,22193:0,22194:0,22195:0,22196:0,22197:0,22234:0,22235:0,22236:0,22332:0,22391:0,22392:0,22521:0,22522:0,22523:0,22524:0,22525:0,22700:0,22770:0,22780:0,22832:0,22991:0,22992:0,22993:0,22994:0,23028:0,23029:0,23030:0,23031:0,23032:0,23033:0,23034:0,23035:0,23036:0,23037:0,23038:0,23090:0,23095:0,23239:0,23240:0,23433:0,23700:0,23830:0,23831:0,23832:0,23833:0,23834:0,23835:0,23836:0,23837:0,23838:0,23839:0,23840:0,23841:0,23842:0,23843:0,23844:0,23845:0,23846:0,23847:0,23848:0,23849:0,23850:0,23851:0,23852:0,23853:0,23866:0,23867:0,23868:0,23869:0,23870:0,23871:0,23872:0,23877:0,23878:0,23879:0,23880:0,23881:0,23882:0,23883:0,23884:0,23886:0,23887:0,23888:0,23889:0,23890:0,23891:0,23892:0,23893:0,23894:0,23946:0,23947:0,23948:0,24047:0,24048:0,24100:0,24200:0,24305:0,24306:0,24311:0,24312:0,24313:0,24342:0,24343:0,24344:0,24345:0,24346:0,24347:0,24370:6,24371:6,24372:6,24373:6,24374:6,24375:0,24376:0,24377:0,24378:0,24379:0,24380:0,24381:0,24382:6,24383:0,24500:0,24547:0,24548:0,24571:7,24600:0,24718:0,24719:0,24720:0,24721:0,24817:0,24818:0,24819:0,24820:0,24821:0,24877:0,24878:0,24879:0,24880:0,24881:0,24882:0,24891:0,24892:0,24893:0,25000:0,25231:0,25391:0,25392:0,25393:0,25394:0,25395:0,25828:0,25829:0,25830:0,25831:0,25832:0,25833:0,25834:0,25835:0,25836:0,25837:0,25838:0,25884:0,25932:0,26191:0,26192:0,26193:0,26194:0,26195:0,26237:0,26331:0,26332:0,26391:0,26392:0,26393:0,26432:0,26591:0,26592:0,26632:0,26692:0,26701:0,26702:0,26703:0,26704:0,26705:0,26706:0,26707:0,26708:0,26709:0,26710:0,26711:0,26712:0,26713:0,26714:0,26715:0,26716:0,26717:0,26718:0,26719:0,26720:0,26721:0,26722:0,26729:3,26730:3,26731:3,26732:3,26733:3,26734:3,26735:3,26736:3,26737:3,26738:3,26739:3,26740:3,26741:3,26742:3,26743:3,26744:3,26745:3,26746:3,26747:3,26748:3,26749:3,26750:3,26751:3,26752:3,26753:3,26754:3,26755:3,26756:3,26757:3,26758:3,26759:3,26760:3,26761:3,26762:3,26763:3,26764:3,26765:3,26766:3,26767:3,26768:3,26769:3,26770:3,26771:3,26772:3,26773:3,26774:3,26775:3,26776:3,26777:3,26778:3,26779:3,26780:3,26781:3,26782:3,26783:3,26784:3,26785:3,26786:3,26787:3,26788:3,26789:3,26790:3,26791:3,26792:3,26793:3,26794:3,26795:3,26796:3,26797:3,26798:3,26799:3,26801:3,26802:3,26803:3,26811:3,26812:3,26813:3,26901:0,26902:0,26903:0,26904:0,26905:0,26906:0,26907:0,26908:0,26909:0,26910:0,26911:0,26912:0,26913:0,26914:0,26915:0,26916:0,26917:0,26918:0,26919:0,26920:0,26921:0,26922:0,26923:0,26929:0,26930:0,26931:0,26932:0,26933:0,26934:0,26935:0,26936:0,26937:0,26938:0,26939:0,26940:0,26941:0,26942:0,26943:0,26944:0,26945:0,26946:0,26948:0,26949:0,26950:0,26951:0,26952:0,26953:0,26954:0,26955:0,26956:0,26957:0,26958:0,26959:0,26960:0,26961:0,26962:0,26963:0,26964:0,26965:0,26966:0,26967:0,26968:0,26969:0,26970:0,26971:0,26972:0,26973:0,26974:0,26975:0,26976:0,26977:0,26978:0,26979:0,26980:0,26981:0,26982:0,26983:0,26984:0,26985:0,26986:0,26987:0,26988:0,26989:0,26990:0,26991:0,26992:0,26993:0,26994:0,26995:0,26996:0,26997:0,26998:0,27037:0,27038:0,27039:0,27040:0,27120:0,27200:0,27205:0,27206:0,27207:0,27208:0,27209:0,27210:0,27211:0,27212:0,27213:0,27214:0,27215:0,27216:0,27217:0,27218:0,27219:0,27220:0,27221:0,27222:0,27223:0,27224:0,27225:0,27226:0,27227:0,27228:0,27229:0,27230:0,27231:0,27232:0,27258:0,27259:0,27260:0,27291:8,27292:8,27391:0,27392:0,27393:0,27394:0,27395:0,27396:0,27397:0,27398:0,27429:0,27492:0,27500:0,27561:0,27562:0,27563:0,27564:0,27571:0,27572:0,27573:0,27574:0,27581:0,27582:0,27583:0,27584:0,27591:0,27592:0,27593:0,27594:0,27700:0,28191:0,28192:0,28193:0,28232:0,28348:0,28349:0,28350:0,28351:0,28352:0,28353:0,28354:0,28355:0,28356:0,28357:0,28358:0,28402:0,28403:0,28404:0,28405:0,28406:0,28407:0,28408:0,28409:0,28410:0,28411:0,28412:0,28413:0,28414:0,28415:0,28416:0,28417:0,28418:0,28419:0,28420:0,28421:0,28422:0,28423:0,28424:0,28425:0,28426:0,28427:0,28428:0,28429:0,28430:0,28431:0,28432:0,28462:0,28463:0,28464:0,28465:0,28466:0,28467:0,28468:0,28469:0,28470:0,28471:0,28472:0,28473:0,28474:0,28475:0,28476:0,28477:0,28478:0,28479:0,28480:0,28481:0,28482:0,28483:0,28484:0,28485:0,28486:0,28487:0,28488:0,28489:0,28490:0,28491:0,28492:0,28600:0,28991:0,28992:0,29100:0,29101:0,29118:0,29119:0,29120:0,29121:0,29122:0,29168:0,29169:0,29170:0,29171:0,29172:0,29177:0,29178:0,29179:0,29180:0,29181:0,29182:0,29183:0,29184:0,29185:0,29187:0,29188:0,29189:0,29190:0,29191:0,29192:0,29193:0,29194:0,29195:0,29220:0,29221:0,29333:0,29635:0,29636:0,29738:0,29739:0,29849:0,29850:0,29871:9,29872:10,29873:0,29900:0,29901:0,29902:0,29903:0,30161:0,30162:0,30163:0,30164:0,30165:0,30166:0,30167:0,30168:0,30169:0,30170:0,30171:0,30172:0,30173:0,30174:0,30175:0,30176:0,30177:0,30178:0,30179:0,30200:1,30339:0,30340:0,30491:0,30492:0,30493:0,30494:0,30591:0,30592:0,30729:0,30730:0,30731:0,30732:0,30791:0,30792:0,30800:0,31028:0,31121:0,31154:0,31170:0,31171:0,31251:0,31252:0,31253:0,31254:0,31255:0,31256:0,31257:0,31258:0,31259:0,31265:0,31266:0,31267:0,31268:0,31275:0,31276:0,31277:0,31278:0,31279:0,31281:0,31282:0,31283:0,31284:0,31285:0,31286:0,31287:0,31288:0,31289:0,31290:0,31291:0,31292:0,31293:0,31294:0,31295:0,31296:0,31297:0,31370:0,31461:0,31462:0,31463:0,31464:0,31465:0,31466:0,31467:0,31468:0,31469:0,31491:0,31492:0,31493:0,31494:0,31495:0,31528:0,31529:0,31600:0,31700:0,31838:0,31839:0,31901:0,31917:0,31918:0,31919:0,31920:0,31921:0,31922:0,31971:0,31972:0,31973:0,31974:0,31975:0,31976:0,31977:0,31978:0,31979:0,31980:0,31981:0,31982:0,31983:0,31984:0,31985:0,31986:0,31987:0,31988:0,31989:0,31990:0,31991:0,31992:0,31993:0,31994:0,31995:0,31996:0,31997:0,31998:0,31999:0,32000:0,32001:3,32002:3,32003:3,32005:3,32006:3,32007:3,32008:3,32009:3,32010:3,32011:3,32012:3,32013:3,32014:3,32015:3,32016:3,32017:3,32018:3,32019:3,32020:3,32021:3,32022:3,32023:3,32024:3,32025:3,32026:3,32027:3,32028:3,32029:3,32030:3,32031:3,32033:3,32034:3,32035:3,32036:3,32037:3,32038:3,32039:3,32040:3,32041:3,32042:3,32043:3,32044:3,32045:3,32046:3,32047:3,32048:3,32049:3,32050:3,32051:3,32052:3,32053:3,32054:3,32055:3,32056:3,32057:3,32058:3,32059:3,32060:3,32061:0,32062:0,32064:3,32065:3,32066:3,32067:3,32074:3,32075:3,32076:3,32077:3,32081:0,32082:0,32083:0,32084:0,32085:0,32086:0,32098:0,32099:3,32100:0,32104:0,32107:0,32108:0,32109:0,32110:0,32111:0,32112:0,32113:0,32114:0,32115:0,32116:0,32117:0,32118:0,32119:0,32120:0,32121:0,32122:0,32123:0,32124:0,32125:0,32126:0,32127:0,32128:0,32129:0,32130:0,32133:0,32134:0,32135:0,32136:0,32137:0,32138:0,32139:0,32140:0,32141:0,32142:0,32143:0,32144:0,32145:0,32146:0,32147:0,32148:0,32149:0,32150:0,32151:0,32152:0,32153:0,32154:0,32155:0,32156:0,32157:0,32158:0,32161:0,32164:3,32165:3,32166:3,32167:3,32180:0,32181:0,32182:0,32183:0,32184:0,32185:0,32186:0,32187:0,32188:0,32189:0,32190:0,32191:0,32192:0,32193:0,32194:0,32195:0,32196:0,32197:0,32198:0,32199:0,32201:0,32202:0,32203:0,32204:0,32205:0,32206:0,32207:0,32208:0,32209:0,32210:0,32211:0,32212:0,32213:0,32214:0,32215:0,32216:0,32217:0,32218:0,32219:0,32220:0,32221:0,32222:0,32223:0,32224:0,32225:0,32226:0,32227:0,32228:0,32229:0,32230:0,32231:0,32232:0,32233:0,32234:0,32235:0,32236:0,32237:0,32238:0,32239:0,32240:0,32241:0,32242:0,32243:0,32244:0,32245:0,32246:0,32247:0,32248:0,32249:0,32250:0,32251:0,32252:0,32253:0,32254:0,32255:0,32256:0,32257:0,32258:0,32259:0,32260:0,32301:0,32302:0,32303:0,32304:0,32305:0,32306:0,32307:0,32308:0,32309:0,32310:0,32311:0,32312:0,32313:0,32314:0,32315:0,32316:0,32317:0,32318:0,32319:0,32320:0,32321:0,32322:0,32323:0,32324:0,32325:0,32326:0,32327:0,32328:0,32329:0,32330:0,32331:0,32332:0,32333:0,32334:0,32335:0,32336:0,32337:0,32338:0,32339:0,32340:0,32341:0,32342:0,32343:0,32344:0,32345:0,32346:0,32347:0,32348:0,32349:0,32350:0,32351:0,32352:0,32353:0,32354:0,32355:0,32356:0,32357:0,32358:0,32359:0,32360:0,32601:0,32602:0,32603:0,32604:0,32605:0,32606:0,32607:0,32608:0,32609:0,32610:0,32611:0,32612:0,32613:0,32614:0,32615:0,32616:0,32617:0,32618:0,32619:0,32620:0,32621:0,32622:0,32623:0,32624:0,32625:0,32626:0,32627:0,32628:0,32629:0,32630:0,32631:0,32632:0,32633:0,32634:0,32635:0,32636:0,32637:0,32638:0,32639:0,32640:0,32641:0,32642:0,32643:0,32644:0,32645:0,32646:0,32647:0,32648:0,32649:0,32650:0,32651:0,32652:0,32653:0,32654:0,32655:0,32656:0,32657:0,32658:0,32659:0,32660:0,32661:0,32662:0,32664:3,32665:3,32666:3,32667:3,32701:0,32702:0,32703:0,32704:0,32705:0,32706:0,32707:0,32708:0,32709:0,32710:0,32711:0,32712:0,32713:0,32714:0,32715:0,32716:0,32717:0,32718:0,32719:0,32720:0,32721:0,32722:0,32723:0,32724:0,32725:0,32726:0,32727:0,32728:0,32729:0,32730:0,32731:0,32732:0,32733:0,32734:0,32735:0,32736:0,32737:0,32738:0,32739:0,32740:0,32741:0,32742:0,32743:0,32744:0,32745:0,32746:0,32747:0,32748:0,32749:0,32750:0,32751:0,32752:0,32753:0,32754:0,32755:0,32756:0,32757:0,32758:0,32759:0,32760:0,32761:0,32766:0,53001:0,53002:0,53003:0,53004:0,53008:0,53009:0,53010:0,53011:0,53012:0,53013:0,53014:0,53015:0,53016:0,53017:0,53018:0,53019:0,53021:0,53022:0,53023:0,53024:0,53025:0,53026:0,53027:0,53028:0,53029:0,53030:0,53031:0,53032:0,53034:0,53042:0,53043:0,53044:0,53045:0,53046:0,53048:0,53049:0,54001:0,54002:0,54003:0,54004:0,54008:0,54009:0,54010:0,54011:0,54012:0,54013:0,54014:0,54015:0,54016:0,54017:0,54018:0,54019:0,54021:0,54022:0,54023:0,54024:0,54025:0,54026:0,54027:0,54028:0,54029:0,54030:0,54031:0,54032:0,54034:0,54042:0,54043:0,54044:0,54045:0,54046:0,54048:0,54049:0,54050:0,54051:0,54052:0,54053:0,65061:3,65062:3,65161:0,65163:0,102001:0,102002:0,102003:0,102004:0,102005:0,102006:0,102007:0,102008:0,102009:0,102010:0,102011:0,102012:0,102013:0,102014:0,102015:0,102016:0,102017:0,102018:0,102019:0,102020:0,102021:0,102022:0,102023:0,102024:0,102025:0,102026:0,102027:0,102028:0,102029:0,102030:0,102031:0,102032:0,102033:0,102034:0,102035:0,102036:0,102037:0,102038:0,102039:0,102060:0,102061:0,102062:0,102063:0,102064:11,102065:0,102066:0,102067:0,102068:12,102069:13,102070:0,102071:0,102072:0,102073:0,102074:0,102075:0,102076:0,102077:0,102078:0,102079:0,102090:0,102091:0,102092:0,102093:0,102094:0,102095:0,102096:0,102097:0,102098:0,102099:0,102100:0,102101:0,102102:0,102103:0,102104:0,102105:0,102106:0,102107:0,102108:0,102109:0,102110:0,102111:0,102112:0,102113:0,102114:0,102115:0,102116:0,102117:0,102118:3,102119:4,102120:3,102121:3,102122:0,102123:0,102124:0,102125:0,102126:0,102127:0,102128:0,102129:0,102130:0,102131:0,102132:0,102133:0,102134:0,102135:0,102136:0,102137:0,102138:0,102139:0,102140:0,102141:0,102142:0,102143:0,102144:0,102145:0,102146:0,102147:0,102148:0,102149:0,102150:0,102151:0,102152:0,102153:0,102154:0,102155:0,102156:0,102157:0,102158:0,102159:0,102160:0,102161:0,102162:0,102163:0,102164:0,102165:0,102166:0,102167:0,102168:0,102169:0,102170:0,102171:0,102172:0,102173:0,102174:0,102175:0,102176:0,102177:0,102178:0,102179:0,102180:0,102181:0,102182:0,102183:0,102184:0,102185:0,102186:0,102187:0,102188:0,102189:0,102190:0,102191:0,102192:0,102193:0,102194:0,102195:0,102196:0,102197:0,102198:0,102199:0,102200:0,102201:0,102202:0,102203:0,102205:0,102206:0,102207:0,102208:0,102209:0,102210:0,102211:0,102218:0,102219:3,102220:3,102221:0,102222:0,102223:0,102224:0,102225:0,102226:0,102227:0,102228:0,102229:0,102230:0,102231:0,102232:0,102233:0,102234:0,102235:0,102236:0,102237:0,102238:0,102239:0,102240:0,102241:0,102242:0,102243:0,102244:0,102245:0,102246:0,102248:0,102249:0,102250:0,102251:0,102252:0,102253:0,102254:0,102255:0,102256:0,102257:0,102258:0,102259:0,102260:0,102261:0,102262:0,102263:0,102264:0,102265:0,102266:0,102267:0,102268:0,102269:0,102270:0,102271:0,102272:0,102273:0,102274:0,102275:0,102276:0,102277:0,102278:0,102279:0,102280:0,102281:0,102282:0,102283:0,102284:0,102285:0,102286:0,102287:0,102288:0,102289:0,102290:0,102291:0,102292:0,102293:0,102294:0,102295:0,102296:0,102297:0,102298:0,102300:0,102304:0,102307:0,102308:0,102309:0,102310:0,102311:0,102312:0,102313:0,102314:0,102315:0,102316:0,102317:0,102318:0,102320:0,102321:0,102322:0,102323:0,102324:0,102325:0,102326:0,102327:0,102330:0,102334:0,102335:0,102336:0,102337:0,102338:0,102339:0,102340:0,102341:0,102342:0,102343:0,102344:0,102345:0,102346:0,102347:0,102348:0,102349:0,102350:0,102351:0,102352:0,102353:0,102354:0,102355:0,102356:0,102357:0,102358:0,102361:0,102363:0,102421:0,102422:0,102423:0,102424:0,102425:0,102426:0,102427:0,102428:0,102429:0,102430:0,102431:0,102432:0,102433:0,102434:0,102435:0,102436:0,102437:0,102438:0,102440:0,102441:0,102442:0,102443:0,102444:0,102461:3,102462:3,102463:3,102464:3,102465:3,102466:3,102467:3,102468:3,102469:0,102491:0,102492:0,102570:0,102571:0,102572:0,102573:0,102574:0,102575:0,102576:0,102577:0,102578:0,102579:0,102580:0,102581:0,102582:0,102583:0,102584:0,102591:0,102592:0,102601:0,102602:0,102603:0,102604:3,102605:0,102606:0,102607:0,102608:0,102609:0,102629:3,102630:3,102631:3,102632:3,102633:3,102634:3,102635:3,102636:3,102637:3,102638:3,102639:3,102640:3,102641:3,102642:3,102643:3,102644:3,102645:3,102646:3,102648:3,102649:3,102650:3,102651:3,102652:3,102653:3,102654:3,102655:3,102656:3,102657:3,102658:3,102659:3,102660:3,102661:3,102662:3,102663:3,102664:3,102665:3,102666:3,102667:3,102668:3,102669:3,102670:3,102671:3,102672:3,102673:3,102674:3,102675:3,102676:3,102677:3,102678:3,102679:3,102680:3,102681:3,102682:3,102683:3,102684:3,102685:3,102686:3,102687:3,102688:3,102689:3,102690:3,102691:3,102692:3,102693:3,102694:3,102695:3,102696:3,102697:3,102698:3,102700:3,102704:3,102707:3,102708:3,102709:3,102710:3,102711:3,102712:3,102713:3,102714:3,102715:3,102716:3,102717:3,102718:3,102719:3,102720:3,102721:3,102722:3,102723:3,102724:3,102725:3,102726:3,102727:3,102728:3,102729:3,102730:3,102733:3,102734:3,102735:3,102736:3,102737:3,102738:3,102739:3,102740:3,102741:3,102742:3,102743:3,102744:3,102745:3,102746:3,102747:3,102748:3,102749:3,102750:3,102751:3,102752:3,102753:3,102754:3,102755:3,102756:3,102757:3,102758:3,102761:3,102763:3,102766:3,103300:0,103301:0,103302:0,103303:0,103304:0,103305:0,103306:0,103307:0,103308:0,103309:0,103310:0,103311:0,103312:0,103313:0,103314:0,103315:0,103316:0,103317:0,103318:0,103319:0,103320:0,103321:0,103322:0,103323:0,103324:0,103325:0,103326:0,103327:0,103328:0,103329:0,103330:0,103331:0,103332:0,103333:0,103334:0,103335:0,103336:0,103337:0,103338:0,103339:0,103340:0,103341:0,103342:0,103343:0,103344:0,103345:0,103346:0,103347:0,103348:0,103349:0,103350:0,103351:0,103352:0,103353:0,103354:0,103355:0,103356:0,103357:0,103358:0,103359:0,103360:0,103361:0,103362:0,103363:0,103364:0,103365:0,103366:0,103367:0,103368:0,103369:0,103370:0,103371:0,103400:3,103401:3,103402:3,103403:3,103404:3,103405:3,103406:3,103407:3,103408:3,103409:3,103410:3,103411:3,103412:3,103413:3,103414:3,103415:3,103416:3,103417:3,103418:3,103419:3,103420:3,103421:3,103422:3,103423:3,103424:3,103425:3,103426:3,103427:3,103428:3,103429:3,103430:3,103431:3,103432:3,103433:3,103434:3,103435:3,103436:3,103437:3,103438:3,103439:3,103440:3,103441:3,103442:3,103443:3,103444:3,103445:3,103446:3,103447:3,103448:3,103449:3,103450:3,103451:3,103452:3,103453:3,103454:3,103455:3,103456:3,103457:3,103458:3,103459:3,103460:3,103461:3,103462:3,103463:3,103464:3,103465:3,103466:3,103467:3,103468:3,103469:3,103470:3,103471:3,103528:0,103529:0,103530:0,103531:0,103532:0,103533:0,103534:0,103535:0,103536:0,103537:0,103538:0,103584:0,103600:0,103601:0,103602:0,103603:0,103604:0,103605:0,103606:0,103607:0,103608:0,103609:0,103610:0,103611:0,103612:0,103613:0,103614:0,103615:0,103616:0,103617:0,103618:0,103619:0,103620:0,103621:0,103622:0,103623:0,103624:0,103625:0,103626:0,103627:0,103628:0,103629:0,103630:0,103631:0,103632:0,103633:0,103634:0,103635:0,103636:0,103637:0,103638:0,103639:0,103640:0,103641:0,103642:0,103643:0,103644:0,103645:0,103646:0,103647:0,103648:0,103649:0,103650:0,103651:0,103652:0,103653:0,103654:0,103655:0,103656:0,103657:0,103658:0,103659:0,103660:0,103661:0,103662:0,103663:0,103664:0,103665:0,103666:0,103667:0,103668:0,103669:0,103670:0,103671:0,103672:0,103673:0,103674:0,103675:0,103676:0,103677:0,103678:0,103679:0,103680:0,103681:0,103682:0,103683:0,103684:0,103685:0,103686:0,103687:0,103688:0,103689:0,103690:0,103691:0,103692:0,103693:0,103700:3,103701:3,103702:3,103703:3,103704:3,103705:3,103706:3,103707:3,103708:3,103709:3,103710:3,103711:3,103712:3,103713:3,103714:3,103715:3,103716:3,103717:3,103718:3,103719:3,103720:3,103721:3,103722:3,103723:3,103724:3,103725:3,103726:3,103727:3,103728:3,103729:3,103730:3,103731:3,103732:3,103733:3,103734:3,103735:3,103736:3,103737:3,103738:3,103739:3,103740:3,103741:3,103742:3,103743:3,103744:3,103745:3,103746:3,103747:3,103748:3,103749:3,103750:3,103751:3,103752:3,103753:3,103754:3,103755:3,103756:3,103757:3,103758:3,103759:3,103760:3,103761:3,103762:3,103763:3,103764:3,103765:3,103766:3,103767:3,103768:3,103769:3,103770:3,103771:3,103772:3,103773:3,103774:3,103775:3,103776:3,103777:3,103778:3,103779:3,103780:3,103781:3,103782:3,103783:3,103784:3,103785:3,103786:3,103787:3,103788:3,103789:3,103790:3,103791:3,103792:3,103793:3,103800:0,103801:0,103802:0,103803:0,103804:0,103805:0,103806:0,103807:0,103808:0,103809:0,103810:0,103811:0,103812:0,103813:0,103814:0,103815:0,103816:0,103817:0,103818:0,103819:0,103820:0,103821:0,103822:0,103823:0,103824:0,103825:0,103826:0,103827:0,103828:0,103829:0,103830:0,103831:0,103832:0,103833:0,103834:0,103835:0,103836:0,103837:0,103838:0,103839:0,103840:0,103841:0,103842:0,103843:0,103844:0,103845:0,103846:0,103847:0,103848:0,103849:0,103850:0,103851:0,103852:0,103853:0,103854:0,103855:0,103856:0,103857:0,103858:0,103859:0,103860:0,103861:0,103862:0,103863:0,103864:0,103865:0,103866:0,103867:0,103868:0,103869:0,103870:0,103871:0,103900:3,103901:3,103902:3,103903:3,103904:3,103905:3,103906:3,103907:3,103908:3,103909:3,103910:3,103911:3,103912:3,103913:3,103914:3,103915:3,103916:3,103917:3,103918:3,103919:3,103920:3,103921:3,103922:3,103923:3,103924:3,103925:3,103926:3,103927:3,103928:3,103929:3,103930:3,103931:3,103932:3,103933:3,103934:3,103935:3,103936:3,103937:3,103938:3,103939:3,103940:3,103941:3,103942:3,103943:3,103944:3,103945:3,103946:3,103947:3,103948:3,103949:3,103950:3,103951:3,103952:3,103953:3,103954:3,103955:3,103956:3,103957:3,103958:3,103959:3,103960:3,103961:3,103962:3,103963:3,103964:3,103965:3,103966:3,103967:3,103968:3,103969:3,103970:3,103971:3};}