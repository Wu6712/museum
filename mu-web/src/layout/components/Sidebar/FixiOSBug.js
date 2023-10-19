export default {
  computed: {
    device() {
      return this.$store.state.app.device
    }
  },
  mounted() {
    this.fixBugIniOS()
  },
  methods: {
    // 修复iOS设备上的一个bug
    fixBugIniOS() {
      const $subMenu = this.$refs.subMenu
      if ($subMenu) {
        const handleMouseleave = $subMenu.handleMouseleave
        $subMenu.handleMouseleave = (e) => {
          if (this.device === 'mobile') {
            return
          }
          handleMouseleave(e)
        }
      }
    }
  }
}
