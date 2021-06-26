package com.example.myweather.network

class JhindexBean {
    companion object {

        private var reason: String? = null
        private var result: ResultBean? = null
        private var error_code = 0
    }


    fun getResult(): ResultBean? {
           return result
    }

    class ResultBean {
        /**
         * city : 沈阳
         * life : {"kongtiao":{"v":"开启制暖空调","des":"您将感到有些冷，可以适当开启制暖空调调节室内温度，以免着凉感冒。"},"guomin":{"v":"极不易发","des":"天气条件极不易诱发过敏，可放心外出，享受生活。"},"shushidu":{"v":"较不舒适","des":"白天天气晴好，但仍会使您感觉偏冷，不很舒适，请注意适时添加衣物，以防感冒。"},"chuanyi":{"v":"寒冷","des":"天气寒冷，建议着厚羽绒服、毛皮大衣加厚毛衣等隆冬服装。年老体弱者尤其要注意保暖防冻。"},"diaoyu":{"v":"不宜","des":"天气冷，不适合垂钓。"},"ganmao":{"v":"较易发","des":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"},"ziwaixian":{"v":"弱","des":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"},"xiche":{"v":"较适宜","des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"yundong":{"v":"较不宜","des":"天气较好，但考虑天气寒冷，推荐您进行室内运动，户外运动时请注意保暖并做好准备活动。"},"daisan":{"v":"不带伞","des":"天气较好，您在出门的时候无须带雨伞。"}}
         */
        private var city: String? = null
        private var life: LifeBean? = null

        fun getLife(): LifeBean? {
            return life
        }


    }

    class LifeBean {
        /**
         * chuanyi : {"v":"寒冷","des":"天气寒冷，建议着厚羽绒服、毛皮大衣加厚毛衣等隆冬服装。年老体弱者尤其要注意保暖防冻。"}
         * ganmao : {"v":"较易发","des":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"}
         * ziwaixian : {"v":"弱","des":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
         * xiche : {"v":"较适宜","des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
         */
        private var chuanyi: ChuanyiBean? = null
        private var ganmao: LifeBean.GanmaoBean? = null
        private var ziwaixian: ZiwaixianBean? = null
        private var xiche: XicheBean? = null

        class GanmaoBean{
            /**
             * v : 较易发
             * des : 昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。
             */
            private var v: String? = null
            private var des: String? = null
        }

        class ChuanyiBean {
            /**
             * v : 寒冷
             * des : 天气寒冷，建议着厚羽绒服、毛皮大衣加厚毛衣等隆冬服装。年老体弱者尤其要注意保暖防冻。
             */
            var v: String? = null
            var des: String? = null
        }

        class ZiwaixianBean {
            /**
             * v : 弱
             * des : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
             */
            var v: String? = null
            var des: String? = null
        }
        class XicheBean {
            /**
             * v : 较适宜
             * des : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
             */
            var v: String? = null
            var des: String? = null
        }
    }
}