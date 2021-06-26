package com.example.myweather.network

class jhtemp
{
        private var reason: String? = null
        private var result: ResultBean? = null
        private var error_code = 0

    fun getResult(): jhtemp.ResultBean? {
        return result
    }
    class ResultBean {
        /**
         * city : 沈阳
         * realtime : {"temperature":"-3","info":"晴","wid":"00","direct":"西南风","power":"2级","aqi":"34"}
         * future : [{"date":"2020-12-25","temperature":"-15/-3℃","weather":"晴","wid":{"day":"00","night":"00"},"direct":"西南风转东北风"},{"date":"2020-12-26","temperature":"-10/2℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"东南风"},{"date":"2020-12-27","temperature":"-15/-2℃","weather":"多云转晴","wid":{"day":"01","night":"00"},"direct":"北风"},{"date":"2020-12-28","temperature":"-18/-7℃","weather":"晴转多云","wid":{"day":"00","night":"01"},"direct":"北风"},{"date":"2020-12-29","temperature":"-22/-12℃","weather":"多云转晴","wid":{"day":"01","night":"00"},"direct":"西北风"}]
         */
        private var city: String? = null
        private var realtime: RealtimeBean? = null
        private var future: List<FutureBean?>? = null

        fun getFuture(): List<FutureBean?>? {
            return future
        }

        fun getRealtime(): RealtimeBean? {
            return realtime

        }
        class RealtimeBean {
            /**
             * temperature : -3
             * info : 晴
             * aqi : 34
             */
            private var temperature: String? = null
            private var info: String? = null
            private var aqi: String? = null

            fun getTemperature(): String? {
                return temperature
            }

            fun getInfo(): String? {
                return info

            }
            fun getaqi(): String? {
                return aqi
            }
        }

        class FutureBean {

            /**
             * date : 2020-12-25
             * temperature : -15/-3℃
             * weather : 晴
             */
            var date: String? = null
            var temperature: String? = null
            var weather: String? = null
           /* val templist: List<String> = temperature?.split("/")!!
            val length:Int = templist[1].length
            var toptemp:Int=templist[0].toInt()
            var lowtemp:Int=templist[1].substring(0,length-1).toInt()*/


        }
        class widBean{
            private var day: String? = null
            private var night: String? = null

            fun getDay(): String? {
                return day
            }

            fun setDay(day: String?) {
                this.day = day
            }

            fun getNight(): String? {
                return night
            }

            fun setNight(night: String?) {
                this.night = night
            }
        }
    }


    }