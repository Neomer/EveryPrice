using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class UserSecurityProfile : BaseEntity
    {

        public virtual IUser Owner { get; set; }

        public virtual string Password { get; set; }

    }
}
